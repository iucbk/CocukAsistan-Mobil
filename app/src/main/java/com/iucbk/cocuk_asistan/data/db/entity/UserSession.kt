package com.iucbk.cocuk_asistan.data.db.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 01.04.2020 - 23:39          │
//└─────────────────────────────┘

@Parcelize
@Entity(tableName = "user_session_table")
data class UserSession(
    @PrimaryKey
    val email: String
) : Parcelable