package com.iucbk.cocuk_asistan.ui.splash

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.iucbk.cocuk_asistan.R
import com.iucbk.cocuk_asistan.databinding.FragmentSplashBinding
import com.iucbk.cocuk_asistan.util.extension.viewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * A simple [Fragment] subclass.
 */
class SplashFragment : Fragment(R.layout.fragment_splash), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    private val binding by viewBinding(FragmentSplashBinding::bind)

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
