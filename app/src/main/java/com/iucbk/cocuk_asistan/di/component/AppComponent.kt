package com.iucbk.cocuk_asistan.di.component

import android.app.Application
import com.iucbk.cocuk_asistan.CocukAsistan
import com.iucbk.cocuk_asistan.di.module.AppModule
import com.iucbk.cocuk_asistan.di.module.DatabaseModule
import com.iucbk.cocuk_asistan.di.module.FragmentModule
import com.iucbk.cocuk_asistan.di.module.NetworkModule
import com.iucbk.cocuk_asistan.di.module.ReceiverModule
import com.iucbk.cocuk_asistan.di.module.RepositoryModule
import com.iucbk.cocuk_asistan.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 02.03.2020 - 12:12          │
//└─────────────────────────────┘

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        NetworkModule::class,
        DatabaseModule::class,
        ViewModelModule::class,
        RepositoryModule::class,
        FragmentModule::class,
        ReceiverModule::class
    ]
)

interface AppComponent : AndroidInjector<CocukAsistan> {

    override fun inject(instance: CocukAsistan?)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: Application): Builder

        fun build(): AppComponent
    }

}