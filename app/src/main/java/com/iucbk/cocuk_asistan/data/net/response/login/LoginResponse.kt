package com.iucbk.cocuk_asistan.data.net.response.login

import com.google.gson.annotations.SerializedName


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 17.03.2020 - 23:25          │
//└─────────────────────────────┘

data class LoginResponse(
    val email: String,
    @SerializedName("full_name")
    val fullName: String,
    val token: String
)