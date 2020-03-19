package com.iucbk.cocuk_asistan.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.iucbk.cocuk_asistan.databinding.ItemQuestionAnswerBinding


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 19.03.2020 - 19:13          │
//└─────────────────────────────┘

class QuestionAnswerViewHolder(
    private val binding: ItemQuestionAnswerBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        answer: String,
        position: Int,
        onClickListener: (Int, Boolean, ItemQuestionAnswerBinding) -> Unit,
        realAnswer: Int
    ) {
        binding.txtAnswer.text = answer
        val result = position == realAnswer

        binding.cntAnswer.setOnClickListener {
            onClickListener(position, result, binding)
        }

    }
}