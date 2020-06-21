package com.iucbk.cocuk_asistan.ui.user.session

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.iucbk.cocuk_asistan.data.db.entity.UserSession
import com.iucbk.cocuk_asistan.data.net.response.common.BaseResponse
import com.iucbk.cocuk_asistan.data.net.response.register.GetInfoResponse
import com.iucbk.cocuk_asistan.data.repository.UserRepository
import com.iucbk.cocuk_asistan.util.Result
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 01.04.2020 - 23:29          │
//└─────────────────────────────┘
class SessionViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private var userSessionJob: Job? = null
    private var registeredUserJob: Job? = null

    lateinit var usersSession: MutableLiveData<List<UserSession>>

    val registeredUser by lazy {
        MutableLiveData<Result<BaseResponse<GetInfoResponse?>>>()
    }

    init {
        getUsersSession()
    }

    fun getRegisteredUser(token: String) {
        if (registeredUserJob?.isActive == true) {
            return
        }
        registeredUserJob = launchRegisteredUserJob(token)
    }

    private fun launchRegisteredUserJob(token: String): Job? {
        return viewModelScope.launch {
            val result =
                userRepository.getRegisteredUserInfo(token).also {
                    userRepository.addNewSessionToDB(it.data?.data)
                }
            registeredUser.postValue(result)
        }
    }

    private fun getUsersSession() {
        if (userSessionJob?.isActive == true) {
            return
        }
        userSessionJob = launchUserSessionJob()
    }

    private fun launchUserSessionJob(): Job? {
        return viewModelScope.launch {
            usersSession = liveData {
                val data = userRepository.getUsersSession()
                emitSource(data)
            } as MutableLiveData<List<UserSession>>
        }
    }
}



