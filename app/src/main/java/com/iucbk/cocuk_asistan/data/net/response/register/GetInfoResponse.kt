package com.iucbk.cocuk_asistan.data.net.response.register

import com.google.gson.annotations.SerializedName


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 03.04.2020 - 12:42          │
//└─────────────────────────────┘

data class GetInfoResponse(
    @SerializedName("full_name")
    val fullName: String,
    val email: String
)