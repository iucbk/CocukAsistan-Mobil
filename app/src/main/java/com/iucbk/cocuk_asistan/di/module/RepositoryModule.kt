package com.iucbk.cocuk_asistan.di.module

import com.iucbk.cocuk_asistan.data.repository.UserRepository
import com.iucbk.cocuk_asistan.data.repository.UserRepositoryImpl
import dagger.Binds
import dagger.Module


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 02.03.2020 - 12:12          │
//└─────────────────────────────┘

@Module
abstract class RepositoryModule {

    @Binds
    internal abstract fun providePromotionRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository

}