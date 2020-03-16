package com.iucbk.cocuk_asistan.di.module

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.iucbk.cocuk_asistan.BuildConfig.BASE_URL
import com.iucbk.cocuk_asistan.data.net.ProjectService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 02.03.2020 - 12:12          │
//└─────────────────────────────┘

@Module
class NetworkModule {
    @Singleton
    @Provides
    fun provideOkHttpClient(context: Context): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson, client: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
    }

    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit.Builder): ProjectService {
        return retrofit.baseUrl(BASE_URL)
            .build()
            .create(ProjectService::class.java)
    }
}