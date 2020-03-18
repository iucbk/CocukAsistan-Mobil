package com.iucbk.cocuk_asistan.data.repository

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iucbk.cocuk_asistan.data.model.UserLoginDTO
import com.iucbk.cocuk_asistan.data.model.UserRegisterDTO
import com.iucbk.cocuk_asistan.data.net.ProjectService
import com.iucbk.cocuk_asistan.data.net.response.login.LoginResponse
import com.iucbk.cocuk_asistan.util.Result
import com.iucbk.cocuk_asistan.util.constant.USER_TOKEN
import com.iucbk.cocuk_asistan.util.convertErrorBody
import com.iucbk.cocuk_asistan.util.putData
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 16.03.2020 - 13:26          │
//└─────────────────────────────┘

class UserRepositoryImpl @Inject constructor(
    private val projectService: ProjectService,
    private val sharedPef: SharedPreferences
) : UserRepository, CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    private val registerUserResponse = MutableLiveData<Result<Nothing?>>()

    private val loginUserResponse = MutableLiveData<Result<Nothing?>>()

    override fun registerUser(userRegisterDTO: UserRegisterDTO): LiveData<Result<Nothing?>> {
        launch {
            fetchRegisterResponse(userRegisterDTO)
        }
        return registerUserResponse
    }

    private suspend fun fetchRegisterResponse(userRegisterDTO: UserRegisterDTO) {
        withContext(Dispatchers.IO) {
            try {
                registerUserResponse.postValue(Result.loading())
                val response = projectService.registerUser(userRegisterDTO)
                if (response.isSuccessful) {
                    registerUserResponse.postValue(Result.success(null))
                } else {
                    registerUserResponse.postValue(
                        Result.error(
                            errorCode = convertErrorBody(response.errorBody()!!)?.code,
                            message = convertErrorBody(response.errorBody()!!)?.message
                        )
                    )
                }
            } catch (e: Exception) {
                registerUserResponse.postValue(Result.error(message = e.localizedMessage))
            }
        }
    }


    override fun loginUser(userLoginDTO: UserLoginDTO): LiveData<Result<Nothing?>> {
        launch {
            fetchUserLogin(userLoginDTO)
        }
        return loginUserResponse
    }

    private suspend fun fetchUserLogin(userLoginDTO: UserLoginDTO) {
        withContext(Dispatchers.IO) {
            try {
                loginUserResponse.postValue(Result.loading())
                val response = projectService.loginUser(userLoginDTO)
                if (response.isSuccessful) {
                    if (saveSession(response.body()?.data)) {
                        loginUserResponse.postValue(Result.success(null))
                    }
                } else {
                    loginUserResponse.postValue(
                        Result.error(
                            errorCode = convertErrorBody(response.errorBody()!!)?.code
                        )
                    )
                }
            } catch (e: java.lang.Exception) {
                loginUserResponse.postValue(Result.error(e.localizedMessage))
            }
        }
    }

    private fun saveSession(loginResponse: LoginResponse?): Boolean {
        return sharedPef.putData(USER_TOKEN, loginResponse?.token)
    }
}