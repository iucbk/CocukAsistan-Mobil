package com.iucbk.cocuk_asistan.data.net.response.quiz_questions

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 04.04.2020 - 22:27          │
//└─────────────────────────────┘

@Parcelize
data class QuizQuestions(
    @SerializedName("question_content")
    val question_content: String,
    @SerializedName("options")
    val options: List<String>,
    @SerializedName("true_option")
    val true_option: Int
) : Parcelable