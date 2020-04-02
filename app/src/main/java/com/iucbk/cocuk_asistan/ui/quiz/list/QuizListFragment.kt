package com.iucbk.cocuk_asistan.ui.quiz.list

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.iucbk.cocuk_asistan.R
import com.iucbk.cocuk_asistan.common.BaseFragment
import com.iucbk.cocuk_asistan.data.model.ErrorState
import com.iucbk.cocuk_asistan.databinding.FragmentQuizListBinding
import com.iucbk.cocuk_asistan.ui.adapter.QuizListAdapter
import com.iucbk.cocuk_asistan.util.Status.ERROR
import com.iucbk.cocuk_asistan.util.Status.LOADING
import com.iucbk.cocuk_asistan.util.Status.SUCCESS
import com.iucbk.cocuk_asistan.util.delegate.AutoClearedValue
import com.iucbk.cocuk_asistan.util.extension.showSnackBar
import com.iucbk.cocuk_asistan.util.extension.showToast
import com.iucbk.cocuk_asistan.util.extension.viewBinding
import com.iucbk.cocuk_asistan.util.getErrorStringFromCode

/**
 * A simple [Fragment] subclass.
 */
class QuizListFragment : BaseFragment<QuizListViewModel>(R.layout.fragment_quiz_list) {

    override fun model(): Any = QuizListViewModel::class.java

    private val binding by viewBinding(FragmentQuizListBinding::bind)

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

    private var adapter by AutoClearedValue<QuizListAdapter>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel.setQuizCategoryId(categoryId ?: 0)
    }

    override fun initUserActionObservers() {
        super.initUserActionObservers()

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.srQuizList.setOnRefreshListener {
            viewModel.setQuizCategoryId(categoryId ?: 0)
        }
    }

    override fun initUI() {
        super.initUI()
        binding.txtCategoryName.text = categoryName ?: "Error"

        adapter = QuizListAdapter {
            val action =
                QuizListFragmentDirections.actionQuizListFragmentToQuizQuestionsFragment(it.quiz_id)
            findNavController().navigate(action)
        }.also {
            binding.recycQuizList.adapter = it
        }
    }

    override fun initObservers() {
        super.initObservers()
        viewModel.quizListById.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                SUCCESS -> {
                    if (result.data?.data.isNullOrEmpty().not()) {
                        adapter.submitList(result.data?.data.orEmpty())
                    } else {
                        adapter.submitList(listOf(ErrorState(result.message)))
                    }
                    binding.srQuizList.isRefreshing = false
                }
                ERROR -> {
                    showSnackBar(
                        getErrorStringFromCode(result.errorCode)
                    )
                    showToast(
                        result.message
                    )
                    binding.srQuizList.isRefreshing = false
                }
                LOADING -> {
                    binding.srQuizList.isRefreshing = true
                }
            }
        })
    }
}
