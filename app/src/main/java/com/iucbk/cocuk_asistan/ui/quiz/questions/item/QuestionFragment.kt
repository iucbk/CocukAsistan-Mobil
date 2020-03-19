package com.iucbk.cocuk_asistan.ui.quiz.questions.item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.iucbk.cocuk_asistan.R
import com.iucbk.cocuk_asistan.data.net.response.quiz_questions.QuizQuestionsResponse
import com.iucbk.cocuk_asistan.databinding.FragmentQuestionBinding
import com.iucbk.cocuk_asistan.di.ViewModelFactory
import com.iucbk.cocuk_asistan.ui.adapter.QuestionAnswerAdapter
import com.iucbk.cocuk_asistan.util.extension.injectViewModel
import com.iucbk.cocuk_asistan.util.extension.showToast
import dagger.android.support.DaggerFragment
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class QuestionFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: QuestionViewModel

    private lateinit var adapter: QuestionAnswerAdapter
    private lateinit var questionData: QuizQuestionsResponse

    private var isItemSelectable = true

    private val binding by lazy {
        FragmentQuestionBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            questionData = it.getParcelable(QUESTION_DATA)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = injectViewModel(viewModelFactory)
        initUI()
        return binding.root
    }

    private fun initUI() {
        binding.txtQuestion.text = questionData.quiz_title

        adapter = QuestionAnswerAdapter(questionData.true_option) { result, itemBinding ->
            if (isItemSelectable) {
                if (result) {
                    itemBinding.cntAnswer.setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.colorGreen
                        )
                    )
                } else {
                    itemBinding.cntAnswer.setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.colorRed
                        )
                    )
                }
                isItemSelectable = false
            } else {
                showToast(getString(R.string.you_already_select))
            }
        }.also {
            binding.recycAnswers.adapter = it
        }

        adapter.submitList(
            questionData.options.split("\\n")
        )
    }

    companion object {
        const val QUESTION_DATA = "questionData"

        @JvmStatic
        fun newInstance(questionsResponse: QuizQuestionsResponse) =
            QuestionFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(QUESTION_DATA, questionsResponse)
                }
            }
    }

}
