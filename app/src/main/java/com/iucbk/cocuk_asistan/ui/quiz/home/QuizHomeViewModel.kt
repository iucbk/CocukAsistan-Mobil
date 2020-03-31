package com.iucbk.cocuk_asistan.ui.quiz.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iucbk.cocuk_asistan.data.net.response.common.BaseResponse
import com.iucbk.cocuk_asistan.data.net.response.quiz_categories.QuizCategoriesResponse
import com.iucbk.cocuk_asistan.data.repository.QuizRepository
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
//│ 18.03.2020 - 16:00          │
//└─────────────────────────────┘

class QuizHomeViewModel @Inject constructor(
    private val quizRepository: QuizRepository
) : ViewModel() {

    private var quizCategoryJob: Job? = null

    val quizCategories by lazy {
        MutableLiveData<Result<BaseResponse<List<QuizCategoriesResponse>>>>()
    }

    init {
        getQuizCategories()
    }

    private fun getQuizCategories() {
        if (quizCategoryJob?.isActive == true) {
            return
        }
        quizCategoryJob = launchQuizCategoryJob()
    }

    private fun launchQuizCategoryJob(): Job? {
        return viewModelScope.launch {
            quizCategories.postValue(Result.loading())
            quizCategories.postValue(quizRepository.getQuizCategories())
        }
    }
}