package com.iucbk.cocuk_asistan.data.net


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 16.03.2020 - 13:44          │
//└─────────────────────────────┘

data class BaseResponse<T>(
    val code: Int,
    val message: String,
    val data: T
)