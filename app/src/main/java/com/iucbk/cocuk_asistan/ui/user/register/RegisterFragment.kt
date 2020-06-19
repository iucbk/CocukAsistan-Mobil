package com.iucbk.cocuk_asistan.ui.user.register

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.iucbk.cocuk_asistan.R
import com.iucbk.cocuk_asistan.common.BaseFragment
import com.iucbk.cocuk_asistan.data.model.UserRegisterDTO
import com.iucbk.cocuk_asistan.databinding.FragmentRegisterBinding
import com.iucbk.cocuk_asistan.enums.RegisterInputs
import com.iucbk.cocuk_asistan.util.Status.ERROR
import com.iucbk.cocuk_asistan.util.Status.LOADING
import com.iucbk.cocuk_asistan.util.Status.SUCCESS
import com.iucbk.cocuk_asistan.util.extension.getString
import com.iucbk.cocuk_asistan.util.extension.hide
import com.iucbk.cocuk_asistan.util.extension.hideKeyboard
import com.iucbk.cocuk_asistan.util.extension.isEmailValid
import com.iucbk.cocuk_asistan.util.extension.isLengthValid
import com.iucbk.cocuk_asistan.util.extension.show
import com.iucbk.cocuk_asistan.util.extension.showSnackBar
import com.iucbk.cocuk_asistan.util.extension.showToast
import com.iucbk.cocuk_asistan.util.extension.userFilledAllEntries
import com.iucbk.cocuk_asistan.util.extension.viewBinding
import com.iucbk.cocuk_asistan.util.getErrorStringFromCode

/**
 * A simple [Fragment] subclass.
 */
class RegisterFragment : BaseFragment<RegisterViewModel>(R.layout.fragment_register) {

    override fun model(): Any = RegisterViewModel::class.java

    private val binding by viewBinding(FragmentRegisterBinding::bind)

    override fun initUserActionObservers() {
        super.initUserActionObservers()

        binding.btnRegister.setOnClickListener {
            onUserRegister()
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun initObservers() {
        super.initObservers()
        viewModel.registerResult.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                SUCCESS -> {
                    binding.prgBar.hide()
                    showSnackBar(getString(R.string.register_success))
                    navigateScreenToLogin()
                }
                ERROR -> {
                    binding.prgBar.hide()
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

    private fun navigateScreenToLogin() {
        val action =
            RegisterFragmentDirections.actionRegisterFragmentToLoginFragment(null)
        findNavController().navigate(action)
    }

    override fun initUI() {
        super.initUI()
        binding.prgBar.hide()
    }

    private fun onUserRegister() {
        if (isInputsValid()) {
            hideKeyboard()
            viewModel.setRegisterData(
                UserRegisterDTO(
                    full_name = binding.txtUserName.getString(),
                    email = binding.txtUserEmail.getString(),
                    password = binding.txtUserPassword.getString()
                )
            )
        }
    }

    private fun isInputsValid(): Boolean {
        val userName = binding.txtUserName.getString()
        val userEmail = binding.txtUserEmail.getString()
        val userPassword = binding.txtUserPassword.getString()

        val allFields = listOf(
            userName,
            userEmail,
            userPassword
        )
        return if (allFields.userFilledAllEntries()) {
            if (isEmailValid(userEmail)) {
                hideError(RegisterInputs.EMAIL)
                if (isLengthValid(userPassword, 6)) {
                    hideError(RegisterInputs.PASSWORD)
                    if (isLengthValid(userName, 2)) {
                        hideError(RegisterInputs.USERNAME)
                        true
                    } else {
                        showError(RegisterInputs.USERNAME)
                        false
                    }
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
                binding.errorName.hide()
                binding.errorEmail.hide()
            }
            RegisterInputs.EMAIL -> {
                showSnackBar(RegisterInputs.EMAIL.toString())
                binding.errorPassword.hide()
                binding.errorName.hide()
                binding.errorEmail.show()
            }
            RegisterInputs.USERNAME -> {
                showSnackBar(RegisterInputs.USERNAME.toString())
                binding.errorPassword.hide()
                binding.errorName.show()
                binding.errorEmail.hide()
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
            RegisterInputs.USERNAME -> {
                binding.errorName.hide()
            }
        }
    }
}

