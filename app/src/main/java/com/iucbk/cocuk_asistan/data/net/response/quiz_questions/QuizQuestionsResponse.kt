package com.iucbk.cocuk_asistan.data.net.response.quiz_questions


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 19.03.2020 - 13:41          │
//└─────────────────────────────┘

data class QuizQuestionsResponse(
    val quiz_id: Int,
    val quiz_title: String,
    val question_content: String,
    val options: String,
    val true_option: Int
)