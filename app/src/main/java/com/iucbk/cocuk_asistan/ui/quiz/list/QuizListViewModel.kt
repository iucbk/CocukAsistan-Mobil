package com.iucbk.cocuk_asistan.ui.quiz.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iucbk.cocuk_asistan.data.net.response.common.BaseResponse
import com.iucbk.cocuk_asistan.data.repository.QuizRepository
import com.iucbk.cocuk_asistan.ui.adapter.base.BaseQuizList
import com.iucbk.cocuk_asistan.util.Result
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 19.03.2020 - 13:49          │
//└─────────────────────────────┘

class QuizListViewModel @Inject constructor(
    private val quizRepository: QuizRepository
) : ViewModel() {

    private var categoryIdJob: Job? = null

    val quizListById by lazy {
        MutableLiveData<Result<BaseResponse<List<BaseQuizList.QuizListResponse>>>>()
    }

    fun setQuizCategoryId(categoryId: Int) {
        if (categoryIdJob?.isActive == true) {
            return
        }
        categoryIdJob = launchQuizListJob(categoryId)
    }

    private fun launchQuizListJob(categoryId: Int): Job? {
        return viewModelScope.launch {
            quizListById.postValue(Result.loading())
            quizListById.postValue(quizRepository.getQuizList(categoryId))
        }
    }
}