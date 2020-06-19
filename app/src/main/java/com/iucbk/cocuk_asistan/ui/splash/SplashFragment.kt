package com.iucbk.cocuk_asistan.ui.splash

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.iucbk.cocuk_asistan.R
import com.iucbk.cocuk_asistan.databinding.FragmentSplashBinding
import com.iucbk.cocuk_asistan.enums.AuthenticationState
import com.iucbk.cocuk_asistan.ui.main.MainViewModel
import com.iucbk.cocuk_asistan.util.extension.viewBinding
import kotlinx.coroutines.delay

/**
 * A simple [Fragment] subclass.
 */
class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val binding by viewBinding(FragmentSplashBinding::bind)
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onStart() {
        super.onStart()
        lifecycleScope.launchWhenStarted {
            navigateScreenToSwipe()
        }
    }

    private suspend fun navigateScreenToSwipe() {
        delay(2000)
        checkSession()
    }

    private fun checkSession() {
        mainViewModel.authenticationState.observe(viewLifecycleOwner, Observer {
            if (it == AuthenticationState.AUTHENTICATED) {
                val action = SplashFragmentDirections.actionSplashFragmentToHomeFragment()
                findNavController().navigate(action)
            } else {
                val action =
                    SplashFragmentDirections.actionSplashFragmentToSwipeUpScreen()
                findNavController().navigate(action)
            }
        })
    }
}
