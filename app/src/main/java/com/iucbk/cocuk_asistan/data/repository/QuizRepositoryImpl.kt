package com.iucbk.cocuk_asistan.data.repository

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iucbk.cocuk_asistan.data.net.ProjectService
import com.iucbk.cocuk_asistan.data.net.response.quiz_categories.QuizCategoriesResponse
import com.iucbk.cocuk_asistan.data.net.response.quiz_list.QuizListResponse
import com.iucbk.cocuk_asistan.util.Result
import com.iucbk.cocuk_asistan.util.convertErrorBody
import com.iucbk.cocuk_asistan.util.getToken
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


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
) : QuizRepository, CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    private val quizCategoriesResponse = MutableLiveData<Result<List<QuizCategoriesResponse?>>>()

    private val quizListResponse = MutableLiveData<Result<List<QuizListResponse?>>>()

    override fun getQuizCategories(): LiveData<Result<List<QuizCategoriesResponse?>>> {
        launch {
            fetchQuizCategories()
        }
        return quizCategoriesResponse
    }

    private suspend fun fetchQuizCategories() {
        withContext(Dispatchers.IO) {
            try {
                quizCategoriesResponse.postValue(Result.loading())
                val response =
                    projectService.getQuizCategories(getToken(sharedPreferences))
                if (response.isSuccessful) {
                    quizCategoriesResponse.postValue(Result.success(response.body()?.data.orEmpty()))
                } else {
                    quizCategoriesResponse.postValue(
                        Result.error(
                            errorCode = convertErrorBody(response.errorBody()!!)?.code
                        )
                    )
                }
            } catch (e: Exception) {
                quizCategoriesResponse.postValue(Result.error(e.localizedMessage))
            }
        }
    }

    override fun getQuizList(quizId: Int): LiveData<Result<List<QuizListResponse?>>> {
        launch {
            fetchQuizList(quizId)
        }
        return quizListResponse
    }

    private suspend fun fetchQuizList(quizId: Int) {
        withContext(Dispatchers.IO) {
            try {
                quizListResponse.postValue(Result.loading())
                val response =
                    projectService.getQuizList(quizId, getToken(sharedPreferences))
                if (response.isSuccessful) {
                    quizListResponse.postValue(Result.success(response.body()?.data.orEmpty()))
                } else {
                    quizListResponse.postValue(
                        Result.error(
                            errorCode = convertErrorBody(response.errorBody()!!)?.code
                        )
                    )
                }
            } catch (e: java.lang.Exception) {
                quizListResponse.postValue(
                    Result.error(
                        message = e.localizedMessage
                    )
                )
            }
        }
    }

}