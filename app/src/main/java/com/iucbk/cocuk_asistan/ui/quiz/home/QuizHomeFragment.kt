package com.iucbk.cocuk_asistan.ui.quiz.home

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.iucbk.cocuk_asistan.R
import com.iucbk.cocuk_asistan.common.BaseFragment
import com.iucbk.cocuk_asistan.databinding.FragmentQuizHomeBinding
import com.iucbk.cocuk_asistan.ui.adapter.QuizCategoriesAdapter
import com.iucbk.cocuk_asistan.ui.adapter.base.BaseCategoryList
import com.iucbk.cocuk_asistan.util.Status.ERROR
import com.iucbk.cocuk_asistan.util.Status.LOADING
import com.iucbk.cocuk_asistan.util.Status.SUCCESS
import com.iucbk.cocuk_asistan.util.delegate.AutoClearedValue
import com.iucbk.cocuk_asistan.util.extension.gone
import com.iucbk.cocuk_asistan.util.extension.show
import com.iucbk.cocuk_asistan.util.extension.showSnackBar
import com.iucbk.cocuk_asistan.util.extension.showToast
import com.iucbk.cocuk_asistan.util.extension.viewBinding
import com.iucbk.cocuk_asistan.util.getErrorStringFromCode

/**
 * A simple [Fragment] subclass.
 */
class QuizHomeFragment : BaseFragment<QuizHomeViewModel>(R.layout.fragment_quiz_home) {

    override fun model(): Any = QuizHomeViewModel::class.java

    private val binding by viewBinding(FragmentQuizHomeBinding::bind)

    private var adapter by AutoClearedValue<QuizCategoriesAdapter>()

    override fun initObservers() {
        super.initObservers()
        viewModel.quizCategories.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                SUCCESS -> {
                    result.data?.data?.let { categories ->
                        binding.prgBar.gone()
                        if (categories.isNotEmpty()) {
                            adapter.submitList(categories)
                        } else {
                            adapter.submitList(listOf(BaseCategoryList.EmptyState()))
                        }
                    }
                }
                ERROR -> {
                    adapter.submitList(listOf(BaseCategoryList.ErrorState()))
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

    override fun initUserActionObservers() {
        super.initUserActionObservers()
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun initUI() {
        super.initUI()
        binding.prgBar.gone()
        adapter = QuizCategoriesAdapter {
            val action =
                QuizHomeFragmentDirections.actionQuizHomeFragmentToQuizListFragment(it.id, it.name)
            findNavController().navigate(action)
        }.also {
            binding.recycCategories.adapter = it
        }
    }
}
