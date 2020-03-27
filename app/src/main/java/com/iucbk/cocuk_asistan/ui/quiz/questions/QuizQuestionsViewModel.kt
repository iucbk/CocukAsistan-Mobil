package com.iucbk.cocuk_asistan.ui.quiz.questions

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iucbk.cocuk_asistan.data.model.QuizScoreDTO
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
    private var sendQuizScoreJob: Job? = null

    val quizResult by lazy {
        MutableLiveData<Result<BaseResponse<List<QuizQuestionsResponse>>>>()
    }

    val sendingScoreResult by lazy {
        MutableLiveData<Result<BaseResponse<Nothing?>>>()
    }

    fun setQuizId(quizId: Int) {
        if (quizIdJob?.isActive == true) {
            return
        }
        quizIdJob = launchQuizJob(quizId)
    }

    fun setQuizScore(quizScoreDTO: QuizScoreDTO) {
        if (sendQuizScoreJob?.isActive == true) {
            return
        }
        sendQuizScoreJob = launchQuizScoreJob(quizScoreDTO)
    }

    private fun launchQuizJob(quizId: Int): Job? {
        return viewModelScope.launch {
            quizResult.postValue(Result.loading())
            quizResult.postValue(quizRepository.getQuizQuestions(quizId))
        }
    }

    private fun launchQuizScoreJob(quizScoreDTO: QuizScoreDTO): Job? {
        return viewModelScope.launch {
            sendingScoreResult.postValue(Result.loading())
            sendingScoreResult.postValue(quizRepository.sendScoreOfQuiz(quizScoreDTO))
        }
    }
}