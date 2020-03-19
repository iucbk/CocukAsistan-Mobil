package com.iucbk.cocuk_asistan.ui.user.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.iucbk.cocuk_asistan.R
import com.iucbk.cocuk_asistan.data.model.UserLoginDTO
import com.iucbk.cocuk_asistan.databinding.FragmentLoginBinding
import com.iucbk.cocuk_asistan.di.ViewModelFactory
import com.iucbk.cocuk_asistan.enums.RegisterInputs
import com.iucbk.cocuk_asistan.util.Status
import com.iucbk.cocuk_asistan.util.extension.getString
import com.iucbk.cocuk_asistan.util.extension.gone
import com.iucbk.cocuk_asistan.util.extension.hide
import com.iucbk.cocuk_asistan.util.extension.injectViewModel
import com.iucbk.cocuk_asistan.util.extension.isEmailValid
import com.iucbk.cocuk_asistan.util.extension.isLengthValid
import com.iucbk.cocuk_asistan.util.extension.show
import com.iucbk.cocuk_asistan.util.extension.showSnackBar
import com.iucbk.cocuk_asistan.util.extension.showToast
import com.iucbk.cocuk_asistan.util.extension.userFilledAllEntries
import com.iucbk.cocuk_asistan.util.getErrorStringFromCode
import dagger.android.support.DaggerFragment
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: LoginViewModel

    private val binding by lazy {
        FragmentLoginBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = injectViewModel(viewModelFactory)
        binding.prgBar.gone()
        binding.btnLogin.setOnClickListener {
            onUserLogin()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.userLoginResponse.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Status.SUCCESS -> {
                    binding.prgBar.gone()
                    showSnackBar(getString(R.string.login_success))
                    findNavController().navigate(
                        R.id.homeFragment,
                        null,
                        NavOptions.Builder()
                            .setPopUpTo(R.id.registerFragment, true)
                            .setPopUpTo(R.id.loginFragment, true)
                            .setPopUpTo(R.id.swipeUpScreen, true)
                            .build()
                    )
                }
                Status.ERROR -> {
                    binding.prgBar.gone()
                    showSnackBar(
                        getErrorStringFromCode(result.errorCode)
                    )
                    showToast(
                        result.message
                    )
                }
                Status.LOADING -> {
                    binding.prgBar.show()
                }
            }
        })
    }

    private fun onUserLogin() {
        if (isUserDataValid()) {
            viewModel.setUserLoginData(
                UserLoginDTO(
                    email = binding.txtEmail.getString(),
                    password = binding.txtPassword.getString()
                )
            )
        }
    }

    private fun isUserDataValid(): Boolean {
        val email = binding.txtEmail.getString()
        val password = binding.txtPassword.getString()

        val allFields = listOf(
            email,
            password
        )

        return if (userFilledAllEntries(allFields)) {
            if (isEmailValid(email)) {
                hideError(RegisterInputs.EMAIL)
                if (isLengthValid(password, 6)) {
                    hideError(RegisterInputs.PASSWORD)
                    true
                } else {
                    showError(RegisterInputs.PASSWORD)
                    false
                }
            } else {
                showError(RegisterInputs.EMAIL)
                false
            }
        } else {
            showSnackBar(getString(R.string.fill_all_blank))
            false
        }
    }

    private fun showError(registerInputs: RegisterInputs) {
        when (registerInputs) {
            RegisterInputs.PASSWORD -> {
                showSnackBar(RegisterInputs.PASSWORD.toString())
                binding.errorPassword.show()
                binding.errorEmail.hide()
            }
            RegisterInputs.EMAIL -> {
                showSnackBar(RegisterInputs.EMAIL.toString())
                binding.errorPassword.hide()
                binding.errorEmail.show()
            }
            else -> {

            }
        }
    }

    private fun hideError(registerInputs: RegisterInputs) {
        when (registerInputs) {
            RegisterInputs.PASSWORD -> {
                binding.errorPassword.hide()
            }
            RegisterInputs.EMAIL -> {
                binding.errorEmail.hide()
            }
            else -> {

            }
        }
    }
}
