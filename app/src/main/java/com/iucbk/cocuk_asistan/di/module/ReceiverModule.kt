package com.iucbk.cocuk_asistan.di.module

import com.iucbk.cocuk_asistan.receiver.AlarmReceiver
import dagger.Module
import dagger.android.ContributesAndroidInjector


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 11.04.2020 - 15:40          │
//└─────────────────────────────┘

@Module
abstract class ReceiverModule {

    @ContributesAndroidInjector
    internal abstract fun contributeAlarmReceiver(): AlarmReceiver

}