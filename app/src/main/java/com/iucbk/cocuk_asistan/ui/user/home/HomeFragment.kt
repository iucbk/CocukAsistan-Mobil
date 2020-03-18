package com.iucbk.cocuk_asistan.ui.user.home

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.iucbk.cocuk_asistan.databinding.FragmentHomeBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : DaggerFragment() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        initUserActions()
        return binding.root
    }

    private fun initUserActions() {
        binding.incGoQuiz.btnSolveQuiz.setOnClickListener {
            val action =
                HomeFragmentDirections.actionHomeFragmentToQuizHomeFragment()
            findNavController().navigate(action)
        }

        binding.incGoExplore.btnStartExplore.setOnClickListener {
            //TODO will be add
        }
    }

}
