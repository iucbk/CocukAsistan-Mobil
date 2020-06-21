package com.iucbk.cocuk_asistan.data.repository

import com.iucbk.cocuk_asistan.data.net.response.common.BaseResponse
import com.iucbk.cocuk_asistan.util.Result

// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 11.04.2020 - 16:41          │
//└─────────────────────────────┘

interface NotificationRepository {

    suspend fun getNotificationData(): Result<BaseResponse<String?>>

}