package com.iucbk.cocuk_asistan.data.db

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.iucbk.cocuk_asistan.data.db.entity.UserSession


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 02.04.2020 - 00:49          │
//└─────────────────────────────┘

object RoomConverter {
    @androidx.room.TypeConverter
    @JvmStatic
    fun usersSessionsToString(array: List<UserSession>): String {
        return if (array.isEmpty()) {
            ""
        } else {
            Gson().toJson(array)
        }
    }

    @androidx.room.TypeConverter
    @JvmStatic
    fun stringToUsersSession(value: String): List<UserSession> {
        val listType = object : TypeToken<List<UserSession>>() {}.type
        return Gson().fromJson(value, listType)
    }
}