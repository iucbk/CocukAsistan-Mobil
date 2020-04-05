package com.iucbk.cocuk_asistan.ui.quiz.questions.item

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.iucbk.cocuk_asistan.R
import com.iucbk.cocuk_asistan.data.net.response.quiz_questions.QuizQuestions
import com.iucbk.cocuk_asistan.databinding.FragmentQuestionBinding
import com.iucbk.cocuk_asistan.ui.adapter.QuestionAnswerAdapter
import com.iucbk.cocuk_asistan.ui.quiz.questions.QuizQuestionsFragment
import com.iucbk.cocuk_asistan.util.delegate.AutoClearedValue
import com.iucbk.cocuk_asistan.util.extension.showToast
import com.iucbk.cocuk_asistan.util.extension.viewBinding

/**
 * A simple [Fragment] subclass.
 */
class QuestionFragment : Fragment(R.layout.fragment_question) {

    private var adapter by AutoClearedValue<QuestionAnswerAdapter>()
    private lateinit var questionData: QuizQuestions

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

        binding.txtQuestion.text = questionData.question_content

        adapter = QuestionAnswerAdapter(questionData.true_option) { position, result, itemBinding ->
            if (isItemSelectable) {
                if (result) {
                    itemBinding.cntAnswer.setCardBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.colorGreen
                        )
                    )
                    setAnswerWithQuestion(position, true)
                } else {
                    itemBinding.cntAnswer.setCardBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.colorRed
                        )
                    )
                    setAnswerWithQuestion(position, false)
                }
                isItemSelectable = false
            } else {
                showToast(getString(R.string.you_already_select))
            }
        }.also {
            binding.recycAnswers.adapter = it
        }

        adapter.submitList(
            questionData.options
        )
    }

    private fun setAnswerWithQuestion(givenAnswer: Int, isTrue: Boolean) {
        (requireParentFragment() as QuizQuestionsFragment)
            .mapQuestionsWithAnswer.add(givenAnswer to isTrue)
    }

    companion object {
        const val QUESTION_DATA = "questionData"

        @JvmStatic
        fun newInstance(questionsResponse: QuizQuestions) =
            QuestionFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(QUESTION_DATA, questionsResponse)
                }
            }
    }
}
