package com.iucbk.cocuk_asistan.ui.user.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.iucbk.cocuk_asistan.R
import com.iucbk.cocuk_asistan.databinding.FragmentHomeBinding
import com.iucbk.cocuk_asistan.util.extension.viewBinding

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUserActions()
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
