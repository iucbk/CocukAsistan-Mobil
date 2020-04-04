package com.iucbk.cocuk_asistan.di.module

import android.content.Context
import androidx.room.Room
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
    fun provideProjectDatabase(context: Context): ProjectDatabase =
        Room.databaseBuilder(
            context,
            ProjectDatabase::class.java, "cocuk_asistan_database"
        ).fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideUsersSessionDao(projectDatabase: ProjectDatabase): UserSessionDao {
        return projectDatabase.getSessionDao()
    }

}