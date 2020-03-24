package com.iucbk.cocuk_asistan.data.repository

import com.iucbk.cocuk_asistan.data.model.UserLoginDTO
import com.iucbk.cocuk_asistan.data.model.UserRegisterDTO
import com.iucbk.cocuk_asistan.data.net.response.common.BaseResponse
import com.iucbk.cocuk_asistan.data.net.response.login.LoginResponse
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

    suspend fun registerUser(userRegisterDTO: UserRegisterDTO): Result<BaseResponse<Nothing?>>

    suspend fun loginUser(userLoginDTO: UserLoginDTO): Result<BaseResponse<LoginResponse?>>
}