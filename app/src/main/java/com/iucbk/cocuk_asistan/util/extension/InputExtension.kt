package com.iucbk.cocuk_asistan.util.extension

import android.util.Patterns
import android.widget.TextView


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 16.03.2020 - 13:58          │
//└─────────────────────────────┘

fun TextView.getString() = this.text.toString()

fun List<String?>.userFilledAllEntries(): Boolean =
    !this.any {
        it.isNullOrEmpty()
    }

fun isEmailValid(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()

fun isLengthValid(text: String, length: Int) = text.length >= length