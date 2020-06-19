package com.iucbk.cocuk_asistan.util.extension

import android.content.SharedPreferences
import com.google.gson.Gson


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 17.03.2020 - 23:34          │
//└─────────────────────────────┘

fun SharedPreferences.putData(key: String, value: Any?) =
    this.putValue(key, value)

fun SharedPreferences.getData(key: String, defValue: Any): Any? =
    this.getValue(key, defValue)

private fun SharedPreferences.getValue(key: String, defValue: Any): Any =
    when (defValue) {
        is String -> getString(key, defValue)!!
        is Int -> getInt(key, defValue)
        else -> Gson().fromJson(getString(key, ""), defValue::class.java)
    }

private fun SharedPreferences.putValue(key: String, value: Any?) =
    with(this.edit()) {
        when (value) {
            is String -> putString(key, value)
            is Int -> putInt(key, value)
            else -> putString(key, Gson().toJson(value))
        }.commit()
    }
