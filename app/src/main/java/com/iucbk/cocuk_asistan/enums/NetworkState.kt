package com.iucbk.cocuk_asistan.enums


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 23.03.2020 - 17:17          │
//└─────────────────────────────┘

data class NetworkState private constructor(
    val status: Status,
    val msg: String? = null
) {
    companion object {
        val LOADED = NetworkState(Status.SUCCESS)
        val LOADING = NetworkState(Status.RUNNING)
        val EMPTY = NetworkState(Status.EMPTY)
        fun error(msg: String?) = NetworkState(Status.FAILED, msg)
    }


    enum class Status {
        RUNNING,
        SUCCESS,
        FAILED,
        EMPTY
    }
}