package com.iucbk.cocuk_asistan.enums

import android.Manifest

// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 31.03.2020 - 15:56          │
//└─────────────────────────────┘

enum class Permissions(
    val reqId: Int
) {
    CAMERA(1);

    fun getPermissionArray(): Array<String> {
        return when (this) {
            CAMERA -> arrayOf(Manifest.permission.CAMERA)
        }
    }
}