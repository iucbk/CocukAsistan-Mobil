package com.iucbk.cocuk_asistan.ui.swipeup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.iucbk.cocuk_asistan.databinding.FragmentSwipeUpScreenBinding

/**
 * A simple [Fragment] subclass.
 */
class SwipeUpScreen : Fragment() {

    private lateinit var binding: FragmentSwipeUpScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSwipeUpScreenBinding.inflate(layoutInflater)

        return binding.root
    }

}
