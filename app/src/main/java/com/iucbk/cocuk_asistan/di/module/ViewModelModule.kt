package com.iucbk.cocuk_asistan.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.iucbk.cocuk_asistan.di.ViewModelFactory
import com.iucbk.cocuk_asistan.di.key.ViewModelKey
import com.iucbk.cocuk_asistan.ui.user.login.LoginViewModel
import com.iucbk.cocuk_asistan.ui.user.register.RegisterViewModel
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
}