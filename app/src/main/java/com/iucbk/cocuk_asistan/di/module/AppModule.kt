package com.iucbk.cocuk_asistan.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.iucbk.cocuk_asistan.util.constant.SHARED_PREF_NAME
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 02.03.2020 - 12:12          │
//└─────────────────────────────┘

@Module
class AppModule {
    @Singleton
    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun provideSharedHelper(context: Context): SharedPreferences =
        context.getSharedPreferences(
            SHARED_PREF_NAME,
            Context.MODE_PRIVATE
        )
}