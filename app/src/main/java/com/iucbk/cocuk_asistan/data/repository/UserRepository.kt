package com.iucbk.cocuk_asistan.data.repository

import androidx.lifecycle.LiveData
import com.iucbk.cocuk_asistan.data.model.UserLoginDTO
import com.iucbk.cocuk_asistan.data.model.UserRegisterDTO
import com.iucbk.cocuk_asistan.util.Result


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 16.03.2020 - 13:25          │
//└─────────────────────────────┘

interface UserRepository {

    fun registerUser(userRegisterDTO: UserRegisterDTO): LiveData<Result<Nothing?>>

    fun loginUser(userLoginDTO: UserLoginDTO): LiveData<Result<Nothing?>>
}