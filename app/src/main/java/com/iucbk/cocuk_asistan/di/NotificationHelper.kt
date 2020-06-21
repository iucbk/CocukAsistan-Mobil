/*
 * Copyright (c) 2019 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.iucbk.cocuk_asistan.di

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.iucbk.cocuk_asistan.R
import com.iucbk.cocuk_asistan.data.model.notif.NotificationModel
import com.iucbk.cocuk_asistan.ui.main.MainActivity
import com.iucbk.cocuk_asistan.util.constant.ALARM_ITEM
import javax.inject.Inject
import javax.inject.Singleton


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 11.04.2020 - 01:23          │
//└─────────────────────────────┘

@Singleton
class NotificationHelper @Inject constructor(
    private val context: Context,
    private val notificationManager: NotificationManager
) {
    fun createNotificationChannel(
        importance: Int,
        showBadge: Boolean,
        name: String,
        description: String
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = context.getString(R.string.app_name)
            val channel = NotificationChannel(channelId, name, importance)
            channel.description = description
            channel.setShowBadge(showBadge)
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun createNotification(notificationModel: NotificationModel) {
        val groupBuilder = buildGroupNotification(notificationModel)
        val notificationBuilder = buildNotification(notificationModel)
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(notificationModel.id, groupBuilder.build())
        notificationManager.notify(notificationModel.id, notificationBuilder.build())
    }

    private fun buildGroupNotification(notificationModel: NotificationModel): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, context.getString(R.string.app_name)).apply {
            setSmallIcon(R.drawable.ic_minik_kasif_logo_asil_06)
            setContentTitle(notificationModel.title)
            setContentText(
                context.getString(
                    R.string.app_name
                )
            )
            setStyle(
                NotificationCompat.BigTextStyle().bigText(
                    context.getString(
                        R.string.app_name
                    )
                )
            )
            setAutoCancel(true)
            setGroupSummary(true)
            setGroup(context.getString(R.string.app_name))
        }
    }

    private fun buildNotification(notificationModel: NotificationModel): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, context.getString(R.string.app_name)).apply {
            val drawable = R.drawable.ic_minik_kasif_logo_asil_06
            setSmallIcon(drawable)
            setContentTitle("Test Bildirimi")
            setAutoCancel(true)

            setLargeIcon(BitmapFactory.decodeResource(context.resources, drawable))
            setContentText("Test Açıklaması")
            setGroup(context.getString(R.string.app_name))

            val intent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                putExtra(ALARM_ITEM, notificationModel)
            }

            val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
            setContentIntent(pendingIntent)
        }
    }
}