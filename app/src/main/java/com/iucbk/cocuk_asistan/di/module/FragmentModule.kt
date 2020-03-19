package com.iucbk.cocuk_asistan.di.module

import com.iucbk.cocuk_asistan.ui.quiz.home.QuizHomeFragment
import com.iucbk.cocuk_asistan.ui.user.home.HomeFragment
import com.iucbk.cocuk_asistan.ui.user.login.LoginFragment
import com.iucbk.cocuk_asistan.ui.user.register.RegisterFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 02.03.2020 - 12:13          │
//└─────────────────────────────┘

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    internal abstract fun contributeRegisterFragment(): RegisterFragment

    @ContributesAndroidInjector
    internal abstract fun contributeLoginFragment(): LoginFragment

    @ContributesAndroidInjector
    internal abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    internal abstract fun contributeQuizHomeFragment(): QuizHomeFragment

}