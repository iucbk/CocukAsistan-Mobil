package com.iucbk.cocuk_asistan.ui.quiz.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.iucbk.cocuk_asistan.databinding.FragmentQuizListBinding
import com.iucbk.cocuk_asistan.di.ViewModelFactory
import com.iucbk.cocuk_asistan.ui.adapter.QuizListAdapter
import com.iucbk.cocuk_asistan.util.Status.ERROR
import com.iucbk.cocuk_asistan.util.Status.LOADING
import com.iucbk.cocuk_asistan.util.Status.SUCCESS
import com.iucbk.cocuk_asistan.util.extension.gone
import com.iucbk.cocuk_asistan.util.extension.injectViewModel
import com.iucbk.cocuk_asistan.util.extension.show
import com.iucbk.cocuk_asistan.util.extension.showSnackBar
import com.iucbk.cocuk_asistan.util.extension.showToast
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

    private val categoryId by lazy {
        arguments?.let {
            QuizListFragmentArgs.fromBundle(it).categoryId
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
        viewModel.setQuizId(categoryId ?: 0)
        initUI()
        return binding.root
    }

    private fun initUI() {
        binding.prgBar.gone()
        binding.txtCategoryName.text = categoryName ?: "Error"

        adapter = QuizListAdapter {
            val action =
                QuizListFragmentDirections.actionQuizListFragmentToQuizQuestionsFragment(it.quiz_id)
            findNavController().navigate(action)
        }.also {
            binding.recycQuizList.adapter = it
        }
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
                    binding.prgBar.show()
                }
            }
        })
    }

}
