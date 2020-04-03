package com.iucbk.cocuk_asistan.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.iucbk.cocuk_asistan.R
import com.iucbk.cocuk_asistan.databinding.ItemQuizListBinding
import com.iucbk.cocuk_asistan.ui.adapter.base.BaseQuizList


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 19.03.2020 - 14:32          │
//└─────────────────────────────┘

open class QuizListViewHolder(
    private val binding: ItemQuizListBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        quizListResponse: BaseQuizList.QuizListResponse,
        onClickListener: (BaseQuizList.QuizListResponse) -> Unit
    ) {
        binding.btnQuizQuestion.text = quizListResponse.quiz_title

        val solvedStateImage = if (quizListResponse.isSolved != 0) {
            R.drawable.ic_check
        } else {
            R.drawable.ic_cross
        }

        binding.viewQuizState.setImageResource(solvedStateImage)

        binding.btnQuizQuestion.setOnClickListener {
            onClickListener(quizListResponse)
        }
    }
}