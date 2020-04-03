package com.iucbk.cocuk_asistan.util

import android.content.SharedPreferences
import android.util.Log
import com.iucbk.cocuk_asistan.data.net.response.login.LoginResponse
import com.iucbk.cocuk_asistan.util.constant.USER_EMAIL
import com.iucbk.cocuk_asistan.util.constant.USER_FULL_NAME
import com.iucbk.cocuk_asistan.util.constant.USER_TOKEN


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 17.03.2020 - 23:52          │
//└─────────────────────────────┘

fun SharedPreferences.getToken() =
    this.getData(USER_TOKEN, "") as String


fun SharedPreferences.saveSession(loginResponse: LoginResponse?): Boolean {
    return try {
        putData(USER_TOKEN, loginResponse?.token)
        putData(USER_EMAIL, loginResponse?.email)
        putData(USER_FULL_NAME, loginResponse?.fullName)
        true
    } catch (e: Exception) {
        Log.e("Saving Session : ", e.localizedMessage ?: "Saving Session")
        false
    }
}

