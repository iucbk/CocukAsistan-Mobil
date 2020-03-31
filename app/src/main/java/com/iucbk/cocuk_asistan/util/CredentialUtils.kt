package com.iucbk.cocuk_asistan.util

import android.content.SharedPreferences
import com.iucbk.cocuk_asistan.data.net.response.login.LoginResponse
import com.iucbk.cocuk_asistan.util.constant.USER_TOKEN


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 17.03.2020 - 23:52          │
//└─────────────────────────────┘

fun getToken(sharedPref: SharedPreferences) =
    sharedPref.getData(USER_TOKEN, "") as String


fun saveSession(sharedPref: SharedPreferences, loginResponse: LoginResponse?) =
    sharedPref.putData(USER_TOKEN, loginResponse?.token)
