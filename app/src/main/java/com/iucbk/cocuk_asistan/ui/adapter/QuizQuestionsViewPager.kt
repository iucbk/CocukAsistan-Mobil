package com.iucbk.cocuk_asistan.ui.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.iucbk.cocuk_asistan.data.net.response.quiz_questions.QuizQuestionsResponse
import com.iucbk.cocuk_asistan.ui.quiz.questions.item.QuestionFragment


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 19.03.2020 - 18:19          │
//└─────────────────────────────┘

class QuizQuestionsViewPager(
    fragment: Fragment
) : FragmentStateAdapter(
    fragment
) {
    private var questionsList = listOf<QuizQuestionsResponse?>()

    override fun getItemCount(): Int = questionsList.size


    override fun createFragment(position: Int): Fragment =
        QuestionFragment.newInstance(questionsList[position]!!)

    fun setNewQuestionList(questionList: List<QuizQuestionsResponse?>) {
        this.questionsList = questionList
        notifyDataSetChanged()
    }
}