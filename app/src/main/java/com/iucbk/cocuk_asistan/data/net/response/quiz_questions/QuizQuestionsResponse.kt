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
//│ 19.03.2020 - 13:41          │
//└─────────────────────────────┘

@Parcelize
data class QuizQuestionsResponse(
    @SerializedName("quiz_id")
    val quiId: Int,
    @SerializedName("quiz_title")
    val quiz_title: String,
    @SerializedName("questions")
    val questions: List<QuizQuestions>
) : Parcelable