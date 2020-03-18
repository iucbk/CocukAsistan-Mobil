package com.iucbk.cocuk_asistan.ui.user.register

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.iucbk.cocuk_asistan.R
import com.iucbk.cocuk_asistan.data.model.UserRegisterDTO
import com.iucbk.cocuk_asistan.databinding.FragmentRegisterBinding
import com.iucbk.cocuk_asistan.di.ViewModelFactory
import com.iucbk.cocuk_asistan.enums.RegisterInputs
import com.iucbk.cocuk_asistan.util.Status.*
import com.iucbk.cocuk_asistan.util.extension.*
import dagger.android.support.DaggerFragment
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class RegisterFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: RegisterViewModel

    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = injectViewModel(viewModelFactory)
        binding = FragmentRegisterBinding.inflate(layoutInflater)

        binding.btnRegister.setOnClickListener {
            onUserRegister()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.userRegisterResponse.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                SUCCESS -> {
                    showSnackBar(getString(R.string.register_success))
                    val action =
                        RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
                    findNavController().navigate(action)
                }
                ERROR -> {
                    showSnackBar(result.message ?: result.errorCode.toString())
                }
                LOADING -> {
                    //TODO Progress Bar
                    Log.e("LOADING ", "LOADING")
                }
            }
        })
    }

    private fun onUserRegister() {
        if (isInputsValid()) {
            viewModel.setUserRegisterData(
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
        return if (userFilledAllEntries(allFields)) {
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

