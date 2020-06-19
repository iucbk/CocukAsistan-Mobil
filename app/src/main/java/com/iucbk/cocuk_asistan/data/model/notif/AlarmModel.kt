package com.iucbk.cocuk_asistan.data.model.notif

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 11.04.2020 - 19:18          │
//└─────────────────────────────┘

@Parcelize
@Entity(tableName = "alarm_model")
data class AlarmModel(
    @PrimaryKey(autoGenerate = false)
    var token: String = "",
    var id: Int = UUID.randomUUID().mostSignificantBits.toString().takeLast(5).toInt(),
    var hour: Int,
    var min: Int,
    var days: List<String>
) : Parcelable