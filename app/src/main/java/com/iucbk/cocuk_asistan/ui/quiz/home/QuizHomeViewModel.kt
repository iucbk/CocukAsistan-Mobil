package com.iucbk.cocuk_asistan.ui.quiz.home

import androidx.lifecycle.ViewModel
import com.iucbk.cocuk_asistan.data.repository.QuizRepository
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

    private val _quizCategories = quizRepository.getQuizCategories()

    fun getQuizCategories() = _quizCategories
}