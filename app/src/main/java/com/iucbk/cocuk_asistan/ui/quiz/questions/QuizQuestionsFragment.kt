package com.iucbk.cocuk_asistan.ui.quiz.questions

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import cn.pedant.SweetAlert.SweetAlertDialog
import com.iucbk.cocuk_asistan.R
import com.iucbk.cocuk_asistan.common.BaseFragment
import com.iucbk.cocuk_asistan.data.model.QuizScoreDTO
import com.iucbk.cocuk_asistan.databinding.FragmentQuizQuestionsBinding
import com.iucbk.cocuk_asistan.ui.adapter.QuizQuestionsViewPager
import com.iucbk.cocuk_asistan.util.Status.ERROR
import com.iucbk.cocuk_asistan.util.Status.LOADING
import com.iucbk.cocuk_asistan.util.Status.SUCCESS
import com.iucbk.cocuk_asistan.util.delegate.AutoClearedValue
import com.iucbk.cocuk_asistan.util.extension.gone
import com.iucbk.cocuk_asistan.util.extension.show
import com.iucbk.cocuk_asistan.util.extension.showSnackBar
import com.iucbk.cocuk_asistan.util.extension.showToast
import com.iucbk.cocuk_asistan.util.extension.viewBinding
import com.iucbk.cocuk_asistan.util.getErrorStringFromCode

/**
 * A simple [Fragment] subclass.
 */
class QuizQuestionsFragment :
    BaseFragment<QuizQuestionsViewModel>(R.layout.fragment_quiz_questions) {

    override fun model(): Any = QuizQuestionsViewModel::class.java

    private val binding by viewBinding(FragmentQuizQuestionsBinding::bind)

    private val quizId by lazy {
        arguments?.let {
            QuizQuestionsFragmentArgs.fromBundle(it).quizId
        }
    }

    private var questionViewPagerAdapter by AutoClearedValue<QuizQuestionsViewPager>()

    internal val mapQuestionsWithAnswer = ArrayList<Pair<Int, Boolean>>()

    private val alertDialog by lazy {
        SweetAlertDialog(requireContext(), SweetAlertDialog.PROGRESS_TYPE)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setQuizId(quizId ?: 0)
    }

    override fun initUI() {
        super.initUI()
        binding.prgBar.gone()
        questionViewPagerAdapter = QuizQuestionsViewPager(this)

        binding.vpQuestions.adapter = questionViewPagerAdapter
    }

    override fun initUserActionObservers() {
        super.initUserActionObservers()

        binding.btnBack.setOnClickListener {
            with(binding.vpQuestions) {
                if (this.currentItem == 0) {
                    showToast(context.getString(R.string.already_start))
                } else {
                    this.setCurrentItem(this.currentItem - 1, true)
                }
            }
        }

        binding.btnNext.setOnClickListener {
            with(binding.vpQuestions) {
                if (this.currentItem + 1 == questionViewPagerAdapter.itemCount) {
                    if (checkIsSolvedAllQuestion(
                            mapQuestionsWithAnswer.size,
                            questionViewPagerAdapter.itemCount
                        )
                    ) {
                        viewModel.setQuizScore(getScoreOfQuiz())
                    } else {
                        SweetAlertDialog(requireContext(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText(getString(R.string.not_solved_all_test))
                            .setContentText(getString(R.string.solved_all_test))
                            .show()
                    }
                } else {
                    this.setCurrentItem(this.currentItem + 1, true)
                }
            }
        }
    }

    override fun initObservers() {
        super.initObservers()
        viewModel.quizResult.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                SUCCESS -> {
                    binding.prgBar.gone()
                    questionViewPagerAdapter.setNewQuestionList(result.data?.data.orEmpty())
                }
                ERROR -> {
                    binding.prgBar.gone()
                    showSnackBar(
                        getErrorStringFromCode(result.errorCode)
                    )
                    showToast(
                        result.message
                    )
                }
                LOADING -> {
                    binding.prgBar.show()
                }
            }
        })

        viewModel.sendingScoreResult.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                SUCCESS -> {
                    alertDialog.apply {
                        progressHelper.stopSpinning()
                        changeAlertType(SweetAlertDialog.SUCCESS_TYPE)
                        titleText = requireContext().getString(R.string.congrat)
                        contentText = requireContext().getString(R.string.solved_all_test)
                        setConfirmClickListener {
                            it.dismissWithAnimation()
                            findNavController().popBackStack()
                        }.show()
                    }
                }
                ERROR -> {
                    alertDialog.apply {
                        changeAlertType(SweetAlertDialog.ERROR_TYPE)
                        titleText = getString(R.string.went_wrong)
                        contentText = getErrorStringFromCode(result.errorCode)
                    }
                    showToast(
                        result.message
                    )
                }
                LOADING -> {
                    alertDialog.apply {
                        setCancelable(false)
                        progressHelper.spin()
                        titleText = getString(R.string.answer_proccess)
                        show()
                    }
                }
            }
        })
    }

    private fun getScoreOfQuiz(): QuizScoreDTO {
        var score = 0
        mapQuestionsWithAnswer.forEach {
            if (it.second) {
                score += 5
            }
        }
        return QuizScoreDTO(
            score,
            quizId ?: 0
        )
    }

    private fun checkIsSolvedAllQuestion(solvedQuestions: Int, questionCount: Int): Boolean =
        solvedQuestions == questionCount
}
