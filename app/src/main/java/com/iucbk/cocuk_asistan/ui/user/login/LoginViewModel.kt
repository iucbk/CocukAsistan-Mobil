package com.iucbk.cocuk_asistan.ui.user.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iucbk.cocuk_asistan.data.model.UserLoginDTO
import com.iucbk.cocuk_asistan.data.net.response.common.BaseResponse
import com.iucbk.cocuk_asistan.data.net.response.login.LoginResponse
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
//│ 17.03.2020 - 00:59          │
//└─────────────────────────────┘

class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private var loginJob: Job? = null

    val loginResult by lazy {
        MutableLiveData<Result<BaseResponse<LoginResponse?>>>()
    }

    fun setLoginData(loginDTO: UserLoginDTO) {
        if (loginJob?.isActive == true) {
            return
        }
        loginJob = launchLoginJob(loginDTO)
    }


    private fun launchLoginJob(loginDTO: UserLoginDTO): Job? {
        return viewModelScope.launch {
            loginResult.postValue(Result.loading())
            loginResult.postValue(userRepository.loginUser(loginDTO))
        }
    }
}