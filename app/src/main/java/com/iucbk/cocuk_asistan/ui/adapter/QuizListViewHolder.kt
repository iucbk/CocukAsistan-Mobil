package com.iucbk.cocuk_asistan.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.iucbk.cocuk_asistan.data.net.response.quiz_list.QuizListResponse
import com.iucbk.cocuk_asistan.databinding.ItemQuizListBinding


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 19.03.2020 - 14:32          │
//└─────────────────────────────┘

class QuizListViewHolder(
    private val binding: ItemQuizListBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(quizListResponse: QuizListResponse, onClickListener: (QuizListResponse) -> Unit) {
        binding.btnQuizQuestion.text = quizListResponse.quiz_title

        binding.btnQuizQuestion.setOnClickListener {
            onClickListener(quizListResponse)
        }
    }
}