package com.iucbk.cocuk_asistan.ui.quiz.questions

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iucbk.cocuk_asistan.data.net.response.common.BaseResponse
import com.iucbk.cocuk_asistan.data.net.response.quiz_questions.QuizQuestionsResponse
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
//│ 19.03.2020 - 18:00          │
//└─────────────────────────────┘

class QuizQuestionsViewModel @Inject constructor(
    private val quizRepository: QuizRepository
) : ViewModel() {

    private var quizIdJob: Job? = null

    val quizResult by lazy {
        MutableLiveData<Result<BaseResponse<List<QuizQuestionsResponse>>>>()
    }

    fun setQuizId(quizId: Int) {
        if (quizIdJob?.isActive == true) {
            return
        }
        quizIdJob = launchQuizJob(quizId)
    }

    private fun launchQuizJob(quizId: Int): Job? {
        return viewModelScope.launch {
            quizResult.postValue(Result.loading())
            quizResult.postValue(quizRepository.getQuizQuestions(quizId))
        }
    }
}