package com.iucbk.cocuk_asistan.ui.adapter.base


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 03.04.2020 - 23:52          │
//└─────────────────────────────┘

sealed class BaseCategoryList {

    data class ErrorState(
        val stateMessage: String = ""
    ) : BaseCategoryList()

    data class EmptyState(
        val stateMessage: String = ""
    ) : BaseCategoryList()

    data class QuizCategoriesResponse(
        val id: Int,
        val name: String
    ) : BaseCategoryList()

}