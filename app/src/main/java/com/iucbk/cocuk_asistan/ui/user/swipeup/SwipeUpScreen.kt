package com.iucbk.cocuk_asistan.ui.user.swipeup

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.iucbk.cocuk_asistan.R
import com.iucbk.cocuk_asistan.databinding.FragmentSwipeUpScreenBinding
import com.iucbk.cocuk_asistan.util.extension.viewBinding

/**
 * A simple [Fragment] subclass.
 */
class SwipeUpScreen : Fragment(R.layout.fragment_swipe_up_screen) {

    private val binding by viewBinding(FragmentSwipeUpScreenBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegister.setOnClickListener {
            val action =
                SwipeUpScreenDirections.actionSwipeUpScreenToRegisterFragment2()
            findNavController().navigate(action)
        }

        binding.btnLogin.setOnClickListener {
            val action =
                SwipeUpScreenDirections.actionSwipeUpScreenToLoginFragment()
            findNavController().navigate(action)
        }
    }
}
