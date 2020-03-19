package com.iucbk.cocuk_asistan.ui.quiz.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.iucbk.cocuk_asistan.databinding.FragmentQuizListBinding
import com.iucbk.cocuk_asistan.di.ViewModelFactory
import com.iucbk.cocuk_asistan.ui.adapter.QuizListAdapter
import com.iucbk.cocuk_asistan.util.Status.*
import com.iucbk.cocuk_asistan.util.extension.*
import com.iucbk.cocuk_asistan.util.getErrorStringFromCode
import dagger.android.support.DaggerFragment
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class QuizListFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val binding by lazy {
        FragmentQuizListBinding.inflate(layoutInflater)
    }

    private lateinit var viewModel: QuizListViewModel

    private val quizId by lazy {
        arguments?.let {
            QuizListFragmentArgs.fromBundle(it).quizId
        }
    }
    private val categoryName by lazy {
        arguments?.let {
            QuizListFragmentArgs.fromBundle(it).quizCategory
        }
    }

    private lateinit var adapter: QuizListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = injectViewModel(viewModelFactory)
        viewModel.setQuizId(quizId ?: 0)
        binding.txtCategoryName.text = categoryName ?: "Error"

        adapter = QuizListAdapter {
            showToast(it.quiz_title)
        }.also {
            binding.recycQuizList.adapter = it
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.quizList.observe(viewLifecycleOwner, Observer { result ->
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
                    Log.e("Loading : ", "Loading")
                    binding.prgBar.show()
                }
            }
        })
    }

}
