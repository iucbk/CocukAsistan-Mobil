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

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.iucbk.cocuk_asistan.R
import com.iucbk.cocuk_asistan.data.model.notif.AlarmModel
import com.iucbk.cocuk_asistan.receiver.AlarmReceiver
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 11.04.2020 - 02:09          │
//└─────────────────────────────┘

@Singleton
class AlarmScheduler @Inject constructor(
    private val context: Context,
    private val alarmManager: AlarmManager
) {
    fun scheduleAlarmsForReminder(alarmModel: AlarmModel) {
        val days = context.resources.getStringArray(R.array.days)
        alarmModel.days.forEach { day ->
            val alarmIntent = createPendingIntent(alarmModel)
            val dayOfWeek = getDayOfWeek(days, day)
            scheduleAlarm(alarmModel, dayOfWeek, alarmIntent)
        }
    }

    private fun scheduleAlarm(alarmModel: AlarmModel, dayOfWeek: Int, alarmIntent: PendingIntent?) {
        val datetimeToAlarm = Calendar.getInstance(Locale.getDefault()).apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, alarmModel.hour)
            set(Calendar.MINUTE, alarmModel.min)
            set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY)
        }

        val today = Calendar.getInstance(Locale.getDefault())
        if (shouldNotifyToday(dayOfWeek, today, datetimeToAlarm)) {
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                datetimeToAlarm.timeInMillis,
                (1000 * 60 * 60 * 24 * 7).toLong(),
                alarmIntent
            )
            return
        }

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            datetimeToAlarm.timeInMillis,
            (1000 * 60 * 60 * 24 * 7).toLong(),
            alarmIntent
        )
    }

    fun removeAlarmsForReminder(alarmId: Int) {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            action = context.getString(R.string.app_name)
        }
        val alarmIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_CANCEL_CURRENT
        )
        alarmManager.cancel(alarmIntent)
    }

    private fun createPendingIntent(alarmModel: AlarmModel): PendingIntent? {
        val intent = Intent(context.applicationContext, AlarmReceiver::class.java).apply {
            action = context.getString(R.string.app_name)
        }
        return PendingIntent.getBroadcast(
            context,
            0,
            intent,
            0
        )
    }

    private fun shouldNotifyToday(dayOfWeek: Int, today: Calendar, datetimeToAlarm: Calendar) =
        dayOfWeek == today.get(Calendar.DAY_OF_WEEK) &&
                today.get(Calendar.HOUR_OF_DAY) <= datetimeToAlarm.get(Calendar.HOUR_OF_DAY) &&
                today.get(Calendar.MINUTE) <= datetimeToAlarm.get(Calendar.MINUTE)


    private fun getDayOfWeek(days: Array<String>, dayOfWeek: String) =
        when {
            dayOfWeek.equals(days[0], ignoreCase = true) -> Calendar.SUNDAY
            dayOfWeek.equals(days[1], ignoreCase = true) -> Calendar.MONDAY
            dayOfWeek.equals(days[2], ignoreCase = true) -> Calendar.TUESDAY
            dayOfWeek.equals(days[3], ignoreCase = true) -> Calendar.WEDNESDAY
            dayOfWeek.equals(days[4], ignoreCase = true) -> Calendar.THURSDAY
            dayOfWeek.equals(days[5], ignoreCase = true) -> Calendar.FRIDAY
            else -> Calendar.SATURDAY
        }

}