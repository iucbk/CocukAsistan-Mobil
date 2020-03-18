package com.iucbk.cocuk_asistan.ui.quiz.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.iucbk.cocuk_asistan.databinding.FragmentQuizHomeBinding
import com.iucbk.cocuk_asistan.di.ViewModelFactory
import com.iucbk.cocuk_asistan.ui.adapter.QuizCategoriesAdapter
import com.iucbk.cocuk_asistan.util.Status.*
import com.iucbk.cocuk_asistan.util.extension.*
import com.iucbk.cocuk_asistan.util.getErrorStringFromCode
import dagger.android.support.DaggerFragment
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class QuizHomeFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var binding: FragmentQuizHomeBinding
    private lateinit var viewModel: QuizHomeViewModel
    private lateinit var adapter: QuizCategoriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuizHomeBinding.inflate(layoutInflater)
        viewModel = injectViewModel(viewModelFactory)
        initUI()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getQuizCategories().observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                SUCCESS -> {
                    binding.prgBar.gone()
                    adapter.submitList(result.data.orEmpty())
                }
                ERROR -> {
                    binding.prgBar.gone()
                    showSnackBar(
                        getErrorStringFromCode(result.errorCode)
                    )
                    showToast(
                        result.message
                    )
                }
                LOADING -> {
                    binding.prgBar.show()
                }
            }
        })
    }

    private fun initUI() {
        binding.prgBar.gone()

        adapter = QuizCategoriesAdapter {
            showSnackBar(it.name)
        }.also {
            binding.recycCategories.adapter = it
        }
    }
}
