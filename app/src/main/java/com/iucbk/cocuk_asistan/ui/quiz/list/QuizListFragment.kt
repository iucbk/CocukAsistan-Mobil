package com.iucbk.cocuk_asistan.ui.quiz.list

import android.os.Bundle
import android.view.View
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
import com.iucbk.cocuk_asistan.util.extension.gone
import com.iucbk.cocuk_asistan.util.extension.show
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

    private lateinit var adapter: QuizListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setQuizCategoryId(categoryId ?: 0)
    }

    override fun initUI() {
        super.initUI()
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

    override fun initObservers() {
        super.initObservers()
        viewModel.quizListById.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                SUCCESS -> {
                    binding.prgBar.gone()
                    if (result.data?.data.isNullOrEmpty().not()) {
                        adapter.submitList(result.data?.data.orEmpty())
                    } else {
                        adapter.submitList(listOf(ErrorState(result.message)))
                    }
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
