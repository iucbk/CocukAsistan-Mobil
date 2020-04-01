package com.iucbk.cocuk_asistan.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.iucbk.cocuk_asistan.enums.AuthenticationState
import java.io.File


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 28.03.2020 - 19:49          │
//└─────────────────────────────┘

class MainViewModel : ViewModel() {

    private val _imageFile = MutableLiveData<File>()
    val imageFile = _imageFile.switchMap {
        liveData {
            emit(it)
        }
    }

    fun setImageFile(imageFile: File) {
        _imageFile.postValue(imageFile)
    }

    private val _authenticationState = MutableLiveData<AuthenticationState>()
    val authenticationState: LiveData<AuthenticationState> get() = _authenticationState

    fun authenticateUser() {
        _authenticationState.value =
            AuthenticationState.AUTHENTICATED
    }

    fun unAuthenticateUser() {
        _authenticationState.value =
            AuthenticationState.UNAUTHENTICATED
    }

    init {
        _authenticationState.value = AuthenticationState.UNAUTHENTICATED
    }
}