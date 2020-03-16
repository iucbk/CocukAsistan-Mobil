package com.iucbk.cocuk_asistan.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iucbk.cocuk_asistan.data.model.UserRegisterDTO
import com.iucbk.cocuk_asistan.data.net.ProjectService
import com.iucbk.cocuk_asistan.util.Result
import com.iucbk.cocuk_asistan.util.convertErrorBody
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
    private val projectService: ProjectService
) : UserRepository, CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    private val registerUserResponse = MutableLiveData<Result<Nothing?>>()

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
                            errorCode = convertErrorBody(response.errorBody()!!)?.code
                        )
                    )
                }
            } catch (e: Exception) {
                registerUserResponse.postValue(Result.error(message = e.localizedMessage))
            }
        }
    }

}