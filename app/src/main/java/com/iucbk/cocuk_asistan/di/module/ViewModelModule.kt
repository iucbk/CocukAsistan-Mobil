package com.iucbk.cocuk_asistan.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.iucbk.cocuk_asistan.di.ViewModelFactory
import com.iucbk.cocuk_asistan.di.key.ViewModelKey
import com.iucbk.cocuk_asistan.ui.`object`.ImageDetailViewModel
import com.iucbk.cocuk_asistan.ui.quiz.home.QuizHomeViewModel
import com.iucbk.cocuk_asistan.ui.quiz.list.QuizListViewModel
import com.iucbk.cocuk_asistan.ui.quiz.questions.QuizQuestionsViewModel
import com.iucbk.cocuk_asistan.ui.setting.SettingViewModel
import com.iucbk.cocuk_asistan.ui.user.home.HomeViewModel
import com.iucbk.cocuk_asistan.ui.user.login.LoginViewModel
import com.iucbk.cocuk_asistan.ui.user.register.RegisterViewModel
import com.iucbk.cocuk_asistan.ui.user.session.SessionViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 02.03.2020 - 12:13          │
//└─────────────────────────────┘

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(RegisterViewModel::class)
    internal abstract fun bindRegisterViewModel(registerViewModel: RegisterViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    internal abstract fun bindLoginViewModel(loginViewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(QuizHomeViewModel::class)
    internal abstract fun bindQuizHomeViewModel(quizHomeViewModel: QuizHomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(QuizListViewModel::class)
    internal abstract fun bindQuizListViewModel(quizListViewModel: QuizListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(QuizQuestionsViewModel::class)
    internal abstract fun bindQuizQuestionsViewModel(quizQuestionsViewModel: QuizQuestionsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ImageDetailViewModel::class)
    internal abstract fun bindImageDetailFragment(imageDetailViewModel: ImageDetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    internal abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SessionViewModel::class)
    internal abstract fun bindSessionViewModel(sessionViewModel: SessionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingViewModel::class)
    internal abstract fun bindSettingViewModel(settingViewModel: SettingViewModel): ViewModel

}