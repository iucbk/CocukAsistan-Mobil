package com.iucbk.cocuk_asistan.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.iucbk.cocuk_asistan.data.model.PasswordResetDTO
import com.iucbk.cocuk_asistan.data.model.notif.AlarmModel
import com.iucbk.cocuk_asistan.data.net.response.common.BaseResponse
import com.iucbk.cocuk_asistan.data.repository.UserRepository
import com.iucbk.cocuk_asistan.util.Result
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 15.06.2020 - 00:24          │
//└─────────────────────────────┘

class SettingViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    init {
        getAlarmData()
    }

    private var getAlarmJob: Job? = null
    lateinit var getAlarmResult: LiveData<List<AlarmModel>>

    private fun getAlarmData() {
        if (getAlarmJob?.isActive != null) {
            return
        }
        getAlarmJob = launchGetAlarmJob()
    }

    private fun launchGetAlarmJob(): Job? {
        return viewModelScope.launch {
            getAlarmResult = liveData {
                emitSource(userRepository.getAlarmModelFromDB())
            }
        }
    }

    private var setAlarmJob: Job? = null

    fun setAlarmData(alarmModel: AlarmModel) {
        if (setAlarmJob?.isActive != null) {
            return
        }
        setAlarmJob = launchSetAlarmJob(alarmModel)
    }

    private fun launchSetAlarmJob(alarmModel: AlarmModel): Job? {
        return viewModelScope.launch {
            userRepository.setAlarmModelToDB(alarmModel)
            setAlarmJob = null
        }
    }

    private var newPasswordJob: Job? = null
    val newPasswordResult by lazy { MutableLiveData<Result<BaseResponse<Nothing?>>>() }


    fun setNewPasswordDate(passwordResetDTO: PasswordResetDTO) {
        if (newPasswordJob?.isActive != null) {
            return
        }
        newPasswordJob = launchNewPasswordJob(passwordResetDTO)
    }

    private fun launchNewPasswordJob(passwordResetDTO: PasswordResetDTO): Job {
        return viewModelScope.launch {
            newPasswordResult.postValue(Result.loading())
            newPasswordResult.postValue(userRepository.setNewPassword(passwordResetDTO))
        }
    }
}