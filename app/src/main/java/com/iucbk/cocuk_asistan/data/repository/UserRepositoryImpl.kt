package com.iucbk.cocuk_asistan.data.repository

import com.iucbk.cocuk_asistan.common.BaseRepository
import com.iucbk.cocuk_asistan.data.model.UserLoginDTO
import com.iucbk.cocuk_asistan.data.model.UserRegisterDTO
import com.iucbk.cocuk_asistan.data.net.ProjectService
import com.iucbk.cocuk_asistan.data.net.response.common.BaseResponse
import com.iucbk.cocuk_asistan.data.net.response.login.LoginResponse
import com.iucbk.cocuk_asistan.util.Result
import javax.inject.Inject


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 16.03.2020 - 13:26          │
//└─────────────────────────────┘

class UserRepositoryImpl @Inject constructor(
    private val projectService: ProjectService
) : BaseRepository(), UserRepository {

    override suspend fun registerUser(userRegisterDTO: UserRegisterDTO): Result<BaseResponse<Nothing?>> {
        return getResult {
            projectService.registerUser(userRegisterDTO)
        }
    }

    override suspend fun loginUser(userLoginDTO: UserLoginDTO): Result<BaseResponse<LoginResponse?>> {
        return getResult {
            projectService.loginUser(userLoginDTO)
        }
    }
}