package com.iucbk.cocuk_asistan.util


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 02.03.2020 - 12:13          │
//└─────────────────────────────┘

data class Result<out T>(
    val status: Status,
    val data: T?,
    val message: String? = null,
    val errorCode: Int? = null
) {

    companion object {
        fun <T> success(data: T): Result<T>? {
            return Result(
                Status.SUCCESS,
                data,
                null
            )
        }

        fun <T> error(message: String? = null, errorCode: Int? = null, data: T? = null): Result<T> {
            return Result(
                Status.ERROR,
                data,
                message,
                errorCode
            )
        }

        fun <T> loading(data: T? = null): Result<T> {
            return Result(
                Status.LOADING,
                data,
                null
            )
        }
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}