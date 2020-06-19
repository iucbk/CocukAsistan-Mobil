package com.iucbk.cocuk_asistan.receiver

import android.content.Context
import android.content.Intent
import android.util.Log
import com.iucbk.cocuk_asistan.data.model.notif.NotificationModel
import com.iucbk.cocuk_asistan.data.repository.NotificationRepository
import com.iucbk.cocuk_asistan.di.NotificationHelper
import com.iucbk.cocuk_asistan.util.Status
import dagger.android.DaggerBroadcastReceiver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 11.04.2020 - 01:55          │
//└─────────────────────────────┘

class AlarmReceiver : DaggerBroadcastReceiver(), CoroutineScope {

    @Inject
    lateinit var notificationRepository: NotificationRepository

    @Inject
    lateinit var notificationHelper: NotificationHelper

    override fun onReceive(context: Context, intent: Intent?) {
        super.onReceive(context, intent)
        Log.e("Receiver : ", "Alarm Received")
        intent?.action?.let {
            launch {
                getNotificationData()
            }
        }
    }

    private suspend fun getNotificationData() {
        val response = notificationRepository.getNotificationData()
        if (response.status == Status.SUCCESS) {
            Log.e(TAG, response.data?.message ?: "Empty")
            val id = System.currentTimeMillis().toInt()
            val notificationModel = NotificationModel(
                id,
                "Cocuk Asistan",
                response.message ?: "Test Bildirimi"
            )
            notificationHelper.createNotification(notificationModel)
        } else {
            Log.e(TAG, response.message ?: "Empty")
        }
    }

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    companion object {
        private const val TAG = "Alarm Receiver"
    }
}