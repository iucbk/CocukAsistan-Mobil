package com.iucbk.cocuk_asistan.ui.user.session

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.iucbk.cocuk_asistan.R
import com.iucbk.cocuk_asistan.common.BaseFragment
import com.iucbk.cocuk_asistan.data.db.entity.UserSession
import com.iucbk.cocuk_asistan.databinding.FragmentSessionBinding
import com.iucbk.cocuk_asistan.ui.adapter.UsersSessionAdapter
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
class SessionFragment : BaseFragment<SessionViewModel>(R.layout.fragment_session) {

    override fun model() = SessionViewModel::class.java

    private val navArgs by navArgs<SessionFragmentArgs>()

    private val binding by viewBinding(FragmentSessionBinding::bind)

    private var adapter by AutoClearedValue<UsersSessionAdapter>()

    override fun initUI() {
        super.initUI()
        viewModel.getRegisteredUser("${navArgs.deepToken}")
        adapter = UsersSessionAdapter {
            navigateScreenToLogin(it)
        }.also {
            binding.recycSessions.adapter = it
        }
    }

    private fun navigateToSplash() {
        val action = SessionFragmentDirections.actionSessionFragmentToSplashFragment()
        findNavController().navigate(action)
    }

    private fun navigateScreenToLogin(userSession: UserSession?) {
        val action =
            SessionFragmentDirections.actionSessionFragmentToLoginFragment(userSession)
        findNavController().navigate(action)
    }

    override fun initObservers() {
        super.initObservers()

        viewModel.usersSession.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it as ArrayList<UserSession>)
        })

        viewModel.registeredUser.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                SUCCESS -> {
                    binding.prgBar.gone()
                }
                ERROR -> {
                    showSnackBar(getErrorStringFromCode(result.errorCode))
                    showToast(result.message)
                    binding.prgBar.gone()
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
            navigateToSplash()
        }

        binding.btnUseAnotherAccount.setOnClickListener {
            navigateScreenToLogin(null)
        }
    }
}
