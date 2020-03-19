package com.iucbk.cocuk_asistan.ui.quiz.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.iucbk.cocuk_asistan.databinding.FragmentQuizListBinding

/**
 * A simple [Fragment] subclass.
 */
class QuizListFragment : Fragment() {

    private val binding by lazy {
        FragmentQuizListBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

}
