package com.iucbk.cocuk_asistan.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iucbk.cocuk_asistan.databinding.ItemEmptyStateBinding
import com.iucbk.cocuk_asistan.databinding.ItemErrorStateBinding
import com.iucbk.cocuk_asistan.databinding.ItemQuizCategoryBinding
import com.iucbk.cocuk_asistan.ui.adapter.base.BaseCategoryList
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
    private val setOnClickListener: (BaseCategoryList.QuizCategoriesResponse) -> Unit
) : ListAdapter<BaseCategoryList, RecyclerView.ViewHolder>(GenericDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            EMPTY_STATE -> {
                val binding = ItemEmptyStateBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                EmptyStateViewHolder(binding)
            }
            ERROR_STATE -> {
                val binding = ItemErrorStateBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                ErrorStateViewHolder(binding)
            }
            ITEM_FULL_STATE -> {
                val binding = ItemQuizCategoryBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                QuizCategoriesViewHolder(binding)
            }
            else -> {
                val binding = ItemQuizCategoryBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                QuizCategoriesViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            EMPTY_STATE -> (holder as EmptyStateViewHolder).bind()
            ERROR_STATE -> (holder as ErrorStateViewHolder).bind()
            ITEM_FULL_STATE -> {
                (holder as QuizCategoriesViewHolder)
                    .bind(
                        getItem(position) as BaseCategoryList.QuizCategoriesResponse,
                        setOnClickListener
                    )
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is BaseCategoryList.QuizCategoriesResponse -> ITEM_FULL_STATE
            is BaseCategoryList.ErrorState -> ERROR_STATE
            is BaseCategoryList.EmptyState -> EMPTY_STATE
            else -> ITEM_FULL_STATE
        }
    }

    companion object {
        const val EMPTY_STATE = 0
        const val ITEM_FULL_STATE = 1
        const val ERROR_STATE = 2
    }
}