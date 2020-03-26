package com.iucbk.cocuk_asistan.data.repository

import com.iucbk.cocuk_asistan.data.model.QuizScoreDTO
import com.iucbk.cocuk_asistan.data.net.response.common.BaseResponse
import com.iucbk.cocuk_asistan.data.net.response.quiz_categories.QuizCategoriesResponse
import com.iucbk.cocuk_asistan.data.net.response.quiz_list.QuizListResponse
import com.iucbk.cocuk_asistan.data.net.response.quiz_questions.QuizQuestionsResponse
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

    suspend fun getQuizCategories(): Result<BaseResponse<List<QuizCategoriesResponse>>>

    suspend fun getQuizList(categoryId: Int): Result<BaseResponse<List<QuizListResponse>>>

    suspend fun getQuizQuestions(quizId: Int): Result<BaseResponse<List<QuizQuestionsResponse>>>

    suspend fun sendScoreOfQuiz(quizScoreDTO: QuizScoreDTO): Result<BaseResponse<Nothing?>>
}