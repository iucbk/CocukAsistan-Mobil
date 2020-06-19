package com.iucbk.cocuk_asistan.data.model.notif

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 11.04.2020 - 01:33          │
//└─────────────────────────────┘

@Parcelize
data class NotificationModel(
    val id: Int,
    val title: String,
    val description: String
) : Parcelable {
    companion object {
        fun generateDummyModel() =
            NotificationModel(
                1,
                "Test Notification Tittle",
                "Test Notification Description"
            )
    }
}