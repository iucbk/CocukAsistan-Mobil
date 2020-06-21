package com.iucbk.cocuk_asistan

import android.app.Application
import androidx.core.app.NotificationManagerCompat
import com.iucbk.cocuk_asistan.di.NotificationHelper
import com.iucbk.cocuk_asistan.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 16.03.2020 - 13:33          │
//└─────────────────────────────┘

class CocukAsistan : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var notificationHelper: NotificationHelper

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent
            .builder()
            .application(this)
            .build()
            .inject(this)

        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        notificationHelper.createNotificationChannel(
            NotificationManagerCompat.IMPORTANCE_DEFAULT,
            true,
            getString(R.string.notificatin_channel_name),
            getString(R.string.notificatin_channel_name)
        )
    }
}