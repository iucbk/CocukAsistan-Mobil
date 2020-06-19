package com.iucbk.cocuk_asistan.common

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.iucbk.cocuk_asistan.data.net.response.common.ErrorBody
import com.iucbk.cocuk_asistan.util.Result
import okhttp3.ResponseBody
import retrofit2.Response


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 24.03.2020 - 16:10          │
//└─────────────────────────────┘

abstract class BaseRepository {

    internal suspend fun <T> getResult(call: suspend () -> Response<T>): Result<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                response.body()?.let { body ->
                    return Result.success(body)!!
                }
            }
            return this.error(response.errorBody()!!)
        } catch (e: Exception) {
            return Result.error(
                e.localizedMessage ?: "Error"
            )
        }
    }

    private fun <T> error(responseBody: ResponseBody): Result<T> {
        responseBody.source().let {
            val gson = Gson()
            val type = object : TypeToken<ErrorBody>() {}.type
            val errorBody = gson.fromJson<ErrorBody>(responseBody.charStream(), type)
            return Result.error(
                errorCode = errorBody.code,
                message = errorBody.message
            )
        }
    }
}