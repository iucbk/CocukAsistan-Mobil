package com.iucbk.cocuk_asistan.data.repository

import android.content.SharedPreferences
import com.iucbk.cocuk_asistan.common.BaseRepository
import com.iucbk.cocuk_asistan.data.net.ProjectService
import com.iucbk.cocuk_asistan.data.net.response.common.BaseResponse
import com.iucbk.cocuk_asistan.util.Result
import com.iucbk.cocuk_asistan.util.getToken
import javax.inject.Inject


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 11.04.2020 - 16:43          │
//└─────────────────────────────┘

class NotificationRepositoryImpl @Inject constructor(
    private val projectService: ProjectService,
    private val sharedPreferences: SharedPreferences
) : NotificationRepository, BaseRepository() {

    override suspend fun getNotificationData(): Result<BaseResponse<String?>> {
        return getResult {
            projectService.getNotificationType(sharedPreferences.getToken())
        }
    }
}