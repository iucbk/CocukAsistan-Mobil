package com.iucbk.cocuk_asistan.data.model

import com.iucbk.cocuk_asistan.data.model.base.QuizListBase


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 23.03.2020 - 17:32          │
//└─────────────────────────────┘

data class ErrorState(
    val errorMessage: String? = "Something went wrong"
) : QuizListBase()