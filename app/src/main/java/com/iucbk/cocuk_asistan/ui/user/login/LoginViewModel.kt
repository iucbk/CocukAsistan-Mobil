package com.iucbk.cocuk_asistan.ui.user.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.iucbk.cocuk_asistan.data.model.UserLoginDTO
import com.iucbk.cocuk_asistan.data.repository.UserRepository
import javax.inject.Inject


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 17.03.2020 - 00:59          │
//└─────────────────────────────┘

class LoginViewModel @Inject constructor(userRepository: UserRepository) : ViewModel() {

    private val _userLoginData = MutableLiveData<UserLoginDTO>()
    val userLoginResponse = _userLoginData.switchMap {
        userRepository.loginUser(it)
    }

    fun setUserLoginData(userLoginDTO: UserLoginDTO) {
        _userLoginData.postValue(userLoginDTO)
    }
}