package com.iucbk.cocuk_asistan.data.net.response.quiz_list


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 19.03.2020 - 17:47          │
//└─────────────────────────────┘

data class QuizListResponse(
    val quiz_id: Int,
    val quiz_title: String,
    val isSolved: Int
)