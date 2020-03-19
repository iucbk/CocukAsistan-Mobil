package com.iucbk.cocuk_asistan.ui.user.swipeup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.iucbk.cocuk_asistan.databinding.FragmentSwipeUpScreenBinding

/**
 * A simple [Fragment] subclass.
 */
class SwipeUpScreen : Fragment() {

    private val binding by lazy {
        FragmentSwipeUpScreenBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

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

        return binding.root
    }

}
