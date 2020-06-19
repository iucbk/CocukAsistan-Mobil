package com.iucbk.cocuk_asistan.ui.quiz.list

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.iucbk.cocuk_asistan.R
import com.iucbk.cocuk_asistan.common.BaseFragment
import com.iucbk.cocuk_asistan.databinding.FragmentQuizListBinding
import com.iucbk.cocuk_asistan.ui.adapter.QuizListAdapter
import com.iucbk.cocuk_asistan.ui.adapter.base.BaseQuizList
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

    private val navArgs by navArgs<QuizListFragmentArgs>()

    private var adapter by AutoClearedValue<QuizListAdapter>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel.setQuizCategoryId(navArgs.categoryId)
    }

    override fun initUserActionObservers() {
        super.initUserActionObservers()

        binding.btnBack.setOnClickListener {
            navigateToBack()
        }

        binding.srQuizList.setOnRefreshListener {
            viewModel.setQuizCategoryId(navArgs.categoryId)
        }
    }

    override fun initUI() {
        super.initUI()
        binding.txtCategoryName.text = navArgs.quizCategory

        adapter = QuizListAdapter {
            val action =
                QuizListFragmentDirections.actionQuizListFragmentToQuizQuestionsFragment(it.quiz_id)
            findNavController().navigate(action)
        }.also {
            binding.recycQuizList.adapter = it
        }
    }

    private fun navigateToBack() {
        findNavController().popBackStack()
    }

    override fun initObservers() {
        super.initObservers()
        viewModel.quizListById.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                SUCCESS -> {
                    result?.data?.data?.let { quizList ->
                        if (quizList.isNullOrEmpty().not()) {
                            adapter.submitList(quizList)
                        } else {
                            adapter.submitList(listOf(BaseQuizList.EmptyState()))
                        }
                        binding.srQuizList.isRefreshing = false
                    }
                }
                ERROR -> {
                    adapter.submitList(listOf(BaseQuizList.ErrorState()))
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
