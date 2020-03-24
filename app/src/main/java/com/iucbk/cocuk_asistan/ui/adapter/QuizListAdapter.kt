package com.iucbk.cocuk_asistan.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iucbk.cocuk_asistan.data.model.ErrorState
import com.iucbk.cocuk_asistan.data.model.base.QuizListBase
import com.iucbk.cocuk_asistan.data.net.response.quiz_list.QuizListResponse
import com.iucbk.cocuk_asistan.databinding.ItemEmptyStateBinding
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
) : ListAdapter<QuizListBase, RecyclerView.ViewHolder>(GenericDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            EMPTY_STATE -> {
                val binding = ItemEmptyStateBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                EmptyStateViewHolder(binding)
            }
            ITEM_FULL_STATE -> {
                val binding = ItemQuizListBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                QuizListViewHolder(binding)
            }
            else -> {
                val binding = ItemEmptyStateBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                EmptyStateViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            EMPTY_STATE -> {
                (holder as EmptyStateViewHolder).bind(
                    getItem(position) as ErrorState
                )
            }
            ITEM_FULL_STATE -> {
                (holder as QuizListViewHolder).bind(
                    getItem(position) as QuizListResponse,
                    setOnClickListener
                )
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is QuizListResponse -> ITEM_FULL_STATE
            is ErrorState -> EMPTY_STATE
            else -> ITEM_FULL_STATE
        }
    }

    companion object {
        const val EMPTY_STATE = 0
        const val ITEM_FULL_STATE = 1
    }
}