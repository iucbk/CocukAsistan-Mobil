package com.iucbk.cocuk_asistan.ui.quiz.questions

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import cn.pedant.SweetAlert.SweetAlertDialog
import com.iucbk.cocuk_asistan.R
import com.iucbk.cocuk_asistan.common.BaseFragment
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

    internal val mapQuestionsWithAnswer = ArrayList<Pair<Int, Int>>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setQuizId(quizId ?: 0)
        initUI()
        initObservers()
    }

    private fun initUI() {
        binding.prgBar.gone()
        questionViewPagerAdapter = QuizQuestionsViewPager(this)

        binding.vpQuestions.adapter = questionViewPagerAdapter

        binding.btnBack.setOnClickListener {
            with(binding.vpQuestions) {
                if (this.currentItem == 0) {
                    showToast("Zaten İlk Sorudasın")
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
                        SweetAlertDialog(requireContext(), SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Tebrikler")
                            .setContentText("Bütün testleri bitirdin")
                            .setConfirmClickListener {
                                it.dismissWithAnimation()
                                findNavController().popBackStack()
                            }.show()

                    } else {
                        SweetAlertDialog(requireContext(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Çözmedigin Sorular Var")
                            .setContentText("Lütfen bütün soruları çözmeyi dene")
                            .show()
                    }
                } else {
                    this.setCurrentItem(this.currentItem + 1, true)
                }
            }
        }
    }

    private fun initObservers() {
        viewModel.quizQuestions.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                SUCCESS -> {
                    binding.prgBar.gone()
                    questionViewPagerAdapter.setNewQuestionList(result.data.orEmpty())
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
    }

    private fun checkIsSolvedAllQuestion(solvedQuestions: Int, questionCount: Int): Boolean =
        solvedQuestions == questionCount
}
