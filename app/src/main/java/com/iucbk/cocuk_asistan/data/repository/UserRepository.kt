package com.iucbk.cocuk_asistan.data.repository

import androidx.lifecycle.LiveData
import com.iucbk.cocuk_asistan.data.db.entity.UserSession
import com.iucbk.cocuk_asistan.data.model.PasswordResetDTO
import com.iucbk.cocuk_asistan.data.model.UserLoginDTO
import com.iucbk.cocuk_asistan.data.model.UserRegisterDTO
import com.iucbk.cocuk_asistan.data.model.notif.AlarmModel
import com.iucbk.cocuk_asistan.data.net.response.common.BaseResponse
import com.iucbk.cocuk_asistan.data.net.response.login.LoginResponse
import com.iucbk.cocuk_asistan.data.net.response.register.GetInfoResponse
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

    suspend fun getUsersSession(): LiveData<List<UserSession>>

    suspend fun getRegisteredUserInfo(token: String): Result<BaseResponse<GetInfoResponse?>>

    suspend fun addNewSessionToDB(getInfoResponse: GetInfoResponse?)

    suspend fun setNewPassword(passwordResetDTO: PasswordResetDTO): Result<BaseResponse<Nothing?>>

    suspend fun setAlarmModelToDB(alarmModel: AlarmModel)

    suspend fun getAlarmModelFromDB(): LiveData<List<AlarmModel>>
}