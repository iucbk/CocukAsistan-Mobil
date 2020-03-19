package com.iucbk.cocuk_asistan.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.iucbk.cocuk_asistan.databinding.ItemQuestionAnswerBinding
import com.iucbk.cocuk_asistan.util.GenericDiffUtil


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 19.03.2020 - 19:12          │
//└─────────────────────────────┘

class QuestionAnswerAdapter(
    private val realAnswer: Int,
    private val setOnClickLister: (Boolean, ItemQuestionAnswerBinding) -> Unit
) : ListAdapter<String, QuestionAnswerViewHolder>(GenericDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionAnswerViewHolder {
        val binding = ItemQuestionAnswerBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return QuestionAnswerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuestionAnswerViewHolder, position: Int) {
        holder.bind(getItem(position), position, setOnClickLister, realAnswer)
    }

}