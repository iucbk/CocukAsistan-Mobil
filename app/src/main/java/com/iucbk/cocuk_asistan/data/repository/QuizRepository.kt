package com.iucbk.cocuk_asistan.data.repository

import androidx.lifecycle.LiveData
import com.iucbk.cocuk_asistan.data.net.response.quiz_categories.QuizCategoriesResponse
import com.iucbk.cocuk_asistan.data.net.response.quiz_list.QuizListResponse
import com.iucbk.cocuk_asistan.util.Result


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 18.03.2020 - 16:04          │
//└─────────────────────────────┘

interface QuizRepository {

    fun getQuizCategories(): LiveData<Result<List<QuizCategoriesResponse?>>>

    fun getQuizList(categoryId: Int): LiveData<Result<List<QuizListResponse?>>>
}