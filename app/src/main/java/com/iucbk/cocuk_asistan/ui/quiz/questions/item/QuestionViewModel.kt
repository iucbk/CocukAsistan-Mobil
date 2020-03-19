package com.iucbk.cocuk_asistan.ui.quiz.questions.item

import androidx.lifecycle.ViewModel
import com.iucbk.cocuk_asistan.data.repository.QuizRepository
import javax.inject.Inject


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 19.03.2020 - 18:25          │
//└─────────────────────────────┘

class QuestionViewModel @Inject constructor(
    questRepository: QuizRepository
) : ViewModel()