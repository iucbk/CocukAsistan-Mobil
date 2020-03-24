package com.iucbk.cocuk_asistan.ui.quiz.questions.item

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.iucbk.cocuk_asistan.R
import com.iucbk.cocuk_asistan.common.BaseFragment
import com.iucbk.cocuk_asistan.data.net.response.quiz_questions.QuizQuestionsResponse
import com.iucbk.cocuk_asistan.databinding.FragmentQuestionBinding
import com.iucbk.cocuk_asistan.ui.adapter.QuestionAnswerAdapter
import com.iucbk.cocuk_asistan.ui.quiz.questions.QuizQuestionsFragment
import com.iucbk.cocuk_asistan.util.delegate.AutoClearedValue
import com.iucbk.cocuk_asistan.util.extension.showToast
import com.iucbk.cocuk_asistan.util.extension.viewBinding

/**
 * A simple [Fragment] subclass.
 */
class QuestionFragment : BaseFragment<QuestionViewModel>(R.layout.fragment_question) {

    override fun model(): Any = QuestionViewModel::class.java

    private var adapter by AutoClearedValue<QuestionAnswerAdapter>()
    private lateinit var questionData: QuizQuestionsResponse

    private var isItemSelectable = true

    private val binding by viewBinding(FragmentQuestionBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            questionData = it.getParcelable(QUESTION_DATA)!!
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        binding.txtQuestion.text = questionData.quiz_title

        adapter = QuestionAnswerAdapter(questionData.true_option) { position, result, itemBinding ->
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
                setAnswerWithQuestion(position)
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

    private fun setAnswerWithQuestion(givenAnswer: Int) {
        (requireParentFragment() as QuizQuestionsFragment)
            .mapQuestionsWithAnswer.add(givenAnswer to questionData.true_option)
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
