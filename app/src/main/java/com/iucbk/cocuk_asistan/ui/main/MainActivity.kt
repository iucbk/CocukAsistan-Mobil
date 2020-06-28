package com.iucbk.cocuk_asistan.ui.main

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.iucbk.cocuk_asistan.databinding.ActivityMainBinding
import com.iucbk.cocuk_asistan.util.constant.SHARED_PREF_NAME
import com.iucbk.cocuk_asistan.util.constant.USER_TOKEN
import com.iucbk.cocuk_asistan.util.extension.getData

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    private val sharedPreferences: SharedPreferences by lazy {
        getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
    }

    private val firebaseAnalytics by lazy { FirebaseAnalytics.getInstance(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        handleSession()
    }

    private fun handleSession() {
        val userToken = sharedPreferences.getData(USER_TOKEN, "") as String?
        if (userToken.isNullOrEmpty()) {
            firebaseAnalytics.setUserId("unknown")
            viewModel.unAuthenticateUser()
        } else {
            firebaseAnalytics.setUserId(sharedPreferences.getData(USER_TOKEN, "") as String)
            viewModel.authenticateUser()
        }
    }
}

