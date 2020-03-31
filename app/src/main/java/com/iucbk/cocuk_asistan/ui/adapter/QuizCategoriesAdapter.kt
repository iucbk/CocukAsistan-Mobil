package com.iucbk.cocuk_asistan.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.iucbk.cocuk_asistan.data.net.response.quiz_categories.QuizCategoriesResponse
import com.iucbk.cocuk_asistan.databinding.ItemQuizCategoryBinding
import com.iucbk.cocuk_asistan.util.GenericDiffUtil


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 19.03.2020 - 01:01          │
//└─────────────────────────────┘

class QuizCategoriesAdapter(
    private val setOnClickListener: (QuizCategoriesResponse) -> Unit
) : ListAdapter<QuizCategoriesResponse, QuizCategoriesViewHolder>(GenericDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizCategoriesViewHolder {
        val binding = ItemQuizCategoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return QuizCategoriesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuizCategoriesViewHolder, position: Int) {
        holder.bind(getItem(position), setOnClickListener)
    }
}