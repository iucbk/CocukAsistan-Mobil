package com.iucbk.cocuk_asistan.util

import android.content.SharedPreferences
import com.google.gson.Gson
import com.iucbk.cocuk_asistan.util.constant.USER_EMAIL
import com.iucbk.cocuk_asistan.util.constant.USER_TOKEN


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 17.03.2020 - 23:34          │
//└─────────────────────────────┘

fun SharedPreferences.deleteSession(): Boolean {
    return this.edit()
        .remove(USER_EMAIL)
        .remove(USER_TOKEN)
        .commit()
}

fun SharedPreferences.putData(key: String, value: Any?): Boolean {
    return putValue(this, key, value)
}

fun SharedPreferences.getData(key: String, defValue: Any): Any? = getValue(this, key, defValue)

private fun getValue(sharedPreferences: SharedPreferences, key: String, defValue: Any): Any =
    with(sharedPreferences) {
        when (defValue) {
            is String -> getString(key, defValue)!!
            is Int -> getInt(key, defValue)
            else -> Gson().fromJson(getString(key, ""), defValue::class.java)
        }
    }

private fun putValue(sharedPreferences: SharedPreferences, key: String, value: Any?): Boolean {
    return with(sharedPreferences.edit()) {
        when (value) {
            is String -> putString(key, value)
            is Int -> putInt(key, value)
            else -> putString(key, Gson().toJson(value))
        }.commit()
    }
}