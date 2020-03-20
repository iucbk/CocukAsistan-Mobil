package com.iucbk.cocuk_asistan.ui.quiz.questions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import cn.pedant.SweetAlert.SweetAlertDialog
import com.iucbk.cocuk_asistan.databinding.FragmentQuizQuestionsBinding
import com.iucbk.cocuk_asistan.di.ViewModelFactory
import com.iucbk.cocuk_asistan.ui.adapter.QuizQuestionsViewPager
import com.iucbk.cocuk_asistan.util.Status.ERROR
import com.iucbk.cocuk_asistan.util.Status.LOADING
import com.iucbk.cocuk_asistan.util.Status.SUCCESS
import com.iucbk.cocuk_asistan.util.extension.gone
import com.iucbk.cocuk_asistan.util.extension.injectViewModel
import com.iucbk.cocuk_asistan.util.extension.show
import com.iucbk.cocuk_asistan.util.extension.showSnackBar
import com.iucbk.cocuk_asistan.util.extension.showToast
import com.iucbk.cocuk_asistan.util.getErrorStringFromCode
import dagger.android.support.DaggerFragment
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class QuizQuestionsFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: QuizQuestionsViewModel

    private val binding by lazy {
        FragmentQuizQuestionsBinding.inflate(layoutInflater)
    }

    private val quizId by lazy {
        arguments?.let {
            QuizQuestionsFragmentArgs.fromBundle(it).quizId
        }
    }

    private val questionViewPagerAdapter by lazy {
        QuizQuestionsViewPager(this)
    }

    internal val mapQuestionsWithAnswer = ArrayList<Pair<Int, Int>>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = injectViewModel(viewModelFactory)
        viewModel.setQuizId(quizId ?: 0)
        initUI()
        return binding.root
    }

    private fun initUI() {
        binding.prgBar.gone()
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
