package com.iucbk.cocuk_asistan.ui.user.login

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.iucbk.cocuk_asistan.R
import com.iucbk.cocuk_asistan.common.BaseFragment
import com.iucbk.cocuk_asistan.data.model.UserLoginDTO
import com.iucbk.cocuk_asistan.databinding.FragmentLoginBinding
import com.iucbk.cocuk_asistan.enums.RegisterInputs
import com.iucbk.cocuk_asistan.util.Status
import com.iucbk.cocuk_asistan.util.extension.getString
import com.iucbk.cocuk_asistan.util.extension.hide
import com.iucbk.cocuk_asistan.util.extension.isEmailValid
import com.iucbk.cocuk_asistan.util.extension.isLengthValid
import com.iucbk.cocuk_asistan.util.extension.show
import com.iucbk.cocuk_asistan.util.extension.showSnackBar
import com.iucbk.cocuk_asistan.util.extension.showToast
import com.iucbk.cocuk_asistan.util.extension.userFilledAllEntries
import com.iucbk.cocuk_asistan.util.extension.viewBinding
import com.iucbk.cocuk_asistan.util.getErrorStringFromCode
import com.iucbk.cocuk_asistan.util.saveSession
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : BaseFragment<LoginViewModel>(R.layout.fragment_login) {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun model(): Any = LoginViewModel::class.java

    private val binding by viewBinding(FragmentLoginBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserver()

        binding.prgBar.hide()
        binding.btnLogin.setOnClickListener {
            onUserLogin()
        }
    }

    private fun initObserver() {
        viewModel.loginResult.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Status.SUCCESS -> {
                    if (saveSession(sharedPreferences, result.data?.data)) {
                        binding.prgBar.hide()
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
                    } else {
                        showSnackBar(getString(R.string.went_wrong))
                    }
                }
                Status.ERROR -> {
                    binding.prgBar.hide()
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
            viewModel.setLoginData(
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
