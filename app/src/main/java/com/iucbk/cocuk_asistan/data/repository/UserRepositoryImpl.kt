package com.iucbk.cocuk_asistan.data.repository

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import com.iucbk.cocuk_asistan.common.BaseRepository
import com.iucbk.cocuk_asistan.data.db.dao.UserSessionDao
import com.iucbk.cocuk_asistan.data.db.entity.UserSession
import com.iucbk.cocuk_asistan.data.model.PasswordResetDTO
import com.iucbk.cocuk_asistan.data.model.UserLoginDTO
import com.iucbk.cocuk_asistan.data.model.UserRegisterDTO
import com.iucbk.cocuk_asistan.data.model.notif.AlarmModel
import com.iucbk.cocuk_asistan.data.net.ProjectService
import com.iucbk.cocuk_asistan.data.net.response.common.BaseResponse
import com.iucbk.cocuk_asistan.data.net.response.login.LoginResponse
import com.iucbk.cocuk_asistan.data.net.response.register.GetInfoResponse
import com.iucbk.cocuk_asistan.util.Result
import com.iucbk.cocuk_asistan.util.getToken
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
    private val projectService: ProjectService,
    private val userSessionDao: UserSessionDao,
    private val sharedPreferences: SharedPreferences
) : BaseRepository(), UserRepository {

    override suspend fun registerUser(userRegisterDTO: UserRegisterDTO): Result<BaseResponse<Nothing?>> {
        return getResult {
            projectService.registerUser(userRegisterDTO)
        }
    }

    override suspend fun loginUser(userLoginDTO: UserLoginDTO): Result<BaseResponse<LoginResponse?>> {
        return getResult {
            projectService.loginUser(userLoginDTO)
        }.also {
            val userSession = UserSession(
                email = userLoginDTO.email
            )
            userSessionDao.addNewSession(userSession)
        }
    }

    override suspend fun getUsersSession(): LiveData<List<UserSession>> {
        return userSessionDao.getAllUserSession()
    }

    override suspend fun getRegisteredUserInfo(token: String): Result<BaseResponse<GetInfoResponse?>> {
        return getResult {
            projectService.getRegisteredUserInfo(token)
        }
    }

    override suspend fun addNewSessionToDB(getInfoResponse: GetInfoResponse?) {
        getInfoResponse?.let { userSessionDao.addNewSession(UserSession(it.email)) }
    }

    override suspend fun setNewPassword(passwordResetDTO: PasswordResetDTO): Result<BaseResponse<Nothing?>> {
        return getResult {
            projectService.setNewPassword(sharedPreferences.getToken(), passwordResetDTO)
        }
    }

    override suspend fun setAlarmModelToDB(alarmModel: AlarmModel) {
        userSessionDao.setAlarmModel(alarmModel)
    }

    override suspend fun getAlarmModelFromDB(): LiveData<List<AlarmModel>> {
        return userSessionDao.getAlarmModel()
    }
}