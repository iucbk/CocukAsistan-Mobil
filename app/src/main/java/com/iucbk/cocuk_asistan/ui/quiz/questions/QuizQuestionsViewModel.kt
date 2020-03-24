package com.iucbk.cocuk_asistan.ui.quiz.questions

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.iucbk.cocuk_asistan.data.repository.QuizRepository
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
    quizRepository: QuizRepository
) : ViewModel() {

    private val _quizId = MutableLiveData<Int>()
    val quizQuestions = _quizId.switchMap {
        quizRepository.getQuizQuestions(it)
    }

    fun setQuizId(quizId: Int) {
        _quizId.postValue(quizId)
    }
}