package com.iucbk.cocuk_asistan.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.iucbk.cocuk_asistan.data.db.dao.UserSessionDao
import com.iucbk.cocuk_asistan.data.db.entity.UserSession
import com.iucbk.cocuk_asistan.data.model.notif.AlarmModel


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 01.04.2020 - 23:33          │
//└─────────────────────────────┘

@Database(
    entities = [UserSession::class, AlarmModel::class],
    version = 1
)
@TypeConverters(RoomConverter::class)
abstract class ProjectDatabase : RoomDatabase() {

    abstract fun getSessionDao(): UserSessionDao

}