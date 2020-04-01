package com.iucbk.cocuk_asistan.di.module

import com.iucbk.cocuk_asistan.data.db.ProjectDatabase
import com.iucbk.cocuk_asistan.data.db.dao.UserSessionDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 01.04.2020 - 23:31          │
//└─────────────────────────────┘

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideUsersSessionDao(projectDatabase: ProjectDatabase): UserSessionDao {
        return projectDatabase.getSessionDao()
    }

}