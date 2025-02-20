package com.iucbk.cocuk_asistan.util

import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.iucbk.cocuk_asistan.R
import com.iucbk.cocuk_asistan.data.net.response.common.ErrorBody
import okhttp3.ResponseBody


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 16.03.2020 - 13:50          │
//└─────────────────────────────┘

fun convertErrorBody(responseBody: ResponseBody): ErrorBody? {
    return try {
        responseBody.source().let {
            val gson = Gson()
            val type = object : TypeToken<ErrorBody>() {}.type
            return gson.fromJson<ErrorBody>(responseBody.charStream(), type)
        }
    } catch (exception: Exception) {
        null
    }
}

fun Fragment.getErrorStringFromCode(code: Int?): String {
    code.let {
        return when (it) {
            422 -> {
                this.getString(R.string.error_username_password)
            }
            else -> {
                this.getString(R.string.something_went_wrong)
            }
        }
    }
}