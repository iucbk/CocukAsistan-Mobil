package com.iucbk.cocuk_asistan.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.iucbk.cocuk_asistan.data.db.entity.UserSession


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 01.04.2020 - 23:40          │
//└─────────────────────────────┘

@Dao
interface UserSessionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewSession(userSession: UserSession)

    @Query("SELECT * FROM user_session_table")
    fun getAllUserSession(): LiveData<List<UserSession>>

}