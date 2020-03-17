package com.iucbk.cocuk_asistan.data.net

import com.iucbk.cocuk_asistan.data.model.UserLoginDTO
import com.iucbk.cocuk_asistan.data.model.UserRegisterDTO
import com.iucbk.cocuk_asistan.data.net.response.login.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 16.03.2020 - 13:31          │
//└─────────────────────────────┘

interface ProjectService {
    @POST("user/signup")
    suspend fun registerUser(
        @Body userRegisterDTO: UserRegisterDTO
    ): Response<BaseResponse<Nothing?>>

    @POST("user/login")
    suspend fun loginUser(
        @Body userLoginDTO: UserLoginDTO
    ): Response<BaseResponse<LoginResponse?>>
}