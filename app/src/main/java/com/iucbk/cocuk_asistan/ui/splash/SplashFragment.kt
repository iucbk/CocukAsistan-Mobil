package com.iucbk.cocuk_asistan.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.iucbk.cocuk_asistan.databinding.FragmentSplashBinding
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * A simple [Fragment] subclass.
 */
class SplashFragment : Fragment(), CoroutineScope {


    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main


    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        launch {
            navigateScreenToSwipe()
        }
    }

    private suspend fun navigateScreenToSwipe() {
        delay(2000)
        val action =
            SplashFragmentDirections.actionSplashFragmentToSwipeUpScreen()
        findNavController().navigate(action)
    }
}
