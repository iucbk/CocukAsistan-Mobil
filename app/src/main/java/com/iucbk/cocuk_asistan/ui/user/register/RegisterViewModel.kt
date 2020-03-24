package com.iucbk.cocuk_asistan.ui.user.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iucbk.cocuk_asistan.data.model.UserRegisterDTO
import com.iucbk.cocuk_asistan.data.net.response.common.BaseResponse
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
//│ 16.03.2020 - 13:24          │
//└─────────────────────────────┘

class RegisterViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private var registerJob: Job? = null

    val registerResult by lazy {
        MutableLiveData<Result<BaseResponse<Nothing?>>>()
    }

    fun setRegisterData(registerDTO: UserRegisterDTO) {
        if (registerJob?.isActive == true) {
            return
        }
        registerJob = launchRegisterJob(registerDTO)
    }

    private fun launchRegisterJob(registerDTO: UserRegisterDTO): Job? {
        return viewModelScope.launch {
            registerResult.postValue(Result.loading())
            registerResult.postValue(userRepository.registerUser(registerDTO))
        }
    }

}