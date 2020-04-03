package com.iucbk.cocuk_asistan.ui.adapter.base


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 03.04.2020 - 23:43          │
//└─────────────────────────────┘

sealed class BaseQuizList {

    data class ErrorState(
        val stateMessage: String = ""
    ) : BaseQuizList()

    data class EmptyState(
        val stateMessage: String = ""
    ) : BaseQuizList()

    data class QuizListResponse(
        val quiz_id: Int,
        val quiz_title: String,
        val isSolved: Int
    ) : BaseQuizList()

}