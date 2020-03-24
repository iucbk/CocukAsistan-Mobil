package com.iucbk.cocuk_asistan.data.repository

import android.content.SharedPreferences
import com.iucbk.cocuk_asistan.common.BaseRepository
import com.iucbk.cocuk_asistan.data.net.ProjectService
import com.iucbk.cocuk_asistan.data.net.response.common.BaseResponse
import com.iucbk.cocuk_asistan.data.net.response.quiz_categories.QuizCategoriesResponse
import com.iucbk.cocuk_asistan.data.net.response.quiz_list.QuizListResponse
import com.iucbk.cocuk_asistan.data.net.response.quiz_questions.QuizQuestionsResponse
import com.iucbk.cocuk_asistan.util.Result
import com.iucbk.cocuk_asistan.util.getToken
import javax.inject.Inject


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 18.03.2020 - 16:05          │
//└─────────────────────────────┘

class QuizRepositoryImpl @Inject constructor(
    private val projectService: ProjectService,
    private val sharedPreferences: SharedPreferences
) : BaseRepository(), QuizRepository {

    override suspend fun getQuizCategories(): Result<BaseResponse<List<QuizCategoriesResponse>>> {
        return getResult {
            projectService.getQuizCategories(getToken(sharedPreferences))
        }
    }

    override suspend fun getQuizList(categoryId: Int): Result<BaseResponse<List<QuizListResponse>>> {
        return getResult {
            projectService.getQuizzesByCategories(categoryId, getToken(sharedPreferences))
        }
    }

    override suspend fun getQuizQuestions(quizId: Int): Result<BaseResponse<List<QuizQuestionsResponse>>> {
        return getResult {
            projectService.getQuizQuestions(quizId, getToken(sharedPreferences))
        }
    }

}