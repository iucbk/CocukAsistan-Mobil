package com.iucbk.cocuk_asistan.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.iucbk.cocuk_asistan.R
import com.iucbk.cocuk_asistan.data.net.response.quiz_categories.QuizCategoriesResponse
import com.iucbk.cocuk_asistan.databinding.ItemQuizCategoryBinding


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 19.03.2020 - 01:02          │
//└─────────────────────────────┘

class QuizCategoriesViewHolder(
    private val binding: ItemQuizCategoryBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        quizCategoriesResponse: QuizCategoriesResponse,
        onClickListener: (QuizCategoriesResponse) -> Unit
    ) {
        binding.btnQuizCategory.text = quizCategoriesResponse.name
        binding.viewQuizState.setImageResource(R.drawable.ic_check)
        binding.btnQuizCategory.setOnClickListener {
            onClickListener(quizCategoriesResponse)
        }
    }
}