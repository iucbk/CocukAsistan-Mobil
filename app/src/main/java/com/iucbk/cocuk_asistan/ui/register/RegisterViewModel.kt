package com.iucbk.cocuk_asistan.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.iucbk.cocuk_asistan.data.model.UserRegisterDTO
import com.iucbk.cocuk_asistan.data.repository.UserRepository
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
    userRepository: UserRepository
) : ViewModel() {

    private val _userRegisterData = MutableLiveData<UserRegisterDTO>()
    val userRegisterResponse = _userRegisterData.switchMap {
        userRepository.registerUser(it)
    }

    fun setUserRegisterData(userRegisterDTO: UserRegisterDTO) {
        _userRegisterData.postValue(userRegisterDTO)
    }
}