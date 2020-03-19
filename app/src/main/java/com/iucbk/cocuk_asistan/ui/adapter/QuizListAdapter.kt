package com.iucbk.cocuk_asistan.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.iucbk.cocuk_asistan.data.net.response.quiz_list.QuizListResponse
import com.iucbk.cocuk_asistan.databinding.ItemQuizListBinding
import com.iucbk.cocuk_asistan.util.GenericDiffUtil


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 19.03.2020 - 14:32          │
//└─────────────────────────────┘

class QuizListAdapter(
    private val setOnClickListener: (QuizListResponse) -> Unit
) : ListAdapter<QuizListResponse, QuizListViewHolder>(GenericDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizListViewHolder {
        val binding = ItemQuizListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return QuizListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuizListViewHolder, position: Int) {
        holder.bind(getItem(position), setOnClickListener)
    }

}