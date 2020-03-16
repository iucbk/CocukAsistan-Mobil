package com.iucbk.cocuk_asistan.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 16.03.2020 - 13:39          │
//└─────────────────────────────┘

@Parcelize
data class UserRegisterDTO(
    val email: String,
    val fullName: String,
    val password: String
) : Parcelable