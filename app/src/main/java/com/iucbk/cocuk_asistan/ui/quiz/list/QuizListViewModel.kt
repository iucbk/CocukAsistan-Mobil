package com.iucbk.cocuk_asistan.ui.quiz.list

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
//│ 19.03.2020 - 13:49          │
//└─────────────────────────────┘

class QuizListViewModel @Inject constructor(
    private val quizRepository: QuizRepository
) : ViewModel() {

    private val _quizId = MutableLiveData<Int>()
    val quizList = _quizId.switchMap {
        quizRepository.getQuizList(it)
    }

    fun setQuizId(quizId: Int) {
        _quizId.postValue(quizId)
    }
}