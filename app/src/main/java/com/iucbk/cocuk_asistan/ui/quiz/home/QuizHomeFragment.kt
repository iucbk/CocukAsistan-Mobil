package com.iucbk.cocuk_asistan.ui.quiz.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.iucbk.cocuk_asistan.databinding.FragmentQuizHomeBinding

/**
 * A simple [Fragment] subclass.
 */
class QuizHomeFragment : Fragment() {

    private lateinit var binding: FragmentQuizHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuizHomeBinding.inflate(layoutInflater)
        return binding.root
    }

}
