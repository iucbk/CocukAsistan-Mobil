package com.iucbk.cocuk_asistan.ui.user.home

import android.content.SharedPreferences
import android.content.pm.PackageManager
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.iucbk.cocuk_asistan.R
import com.iucbk.cocuk_asistan.common.BaseFragment
import com.iucbk.cocuk_asistan.databinding.FragmentHomeBinding
import com.iucbk.cocuk_asistan.enums.Permissions
import com.iucbk.cocuk_asistan.util.constant.USER_FULL_NAME
import com.iucbk.cocuk_asistan.util.extension.checkPermissions
import com.iucbk.cocuk_asistan.util.extension.hideKeyboard
import com.iucbk.cocuk_asistan.util.extension.requestPermission
import com.iucbk.cocuk_asistan.util.extension.showToast
import com.iucbk.cocuk_asistan.util.extension.viewBinding
import com.iucbk.cocuk_asistan.util.getData
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : BaseFragment<HomeViewModel>(R.layout.fragment_home) {

    override fun model(): Any {
        return HomeViewModel::class.java
    }

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val userName by lazy {
        (sharedPreferences.getData(USER_FULL_NAME, "") as String).substringBefore(" ")
    }

    override fun initUI() {
        super.initUI()
        hideKeyboard()
        handleBackPress()
        binding.include2.txtGreeting.text = String.format(getString(R.string.greeting), userName)
    }

    private fun handleBackPress() {
        var backPressCounter = 0
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (backPressCounter != 0) {
                        requireActivity().finish()
                    } else {
                        showToast(getString(R.string.tap_again))
                        backPressCounter++
                    }
                }
            })
    }

    override fun initUserActionObservers() {
        super.initUserActionObservers()

        binding.incGoQuiz.btnSolveQuiz.setOnClickListener {
            val action =
                HomeFragmentDirections.actionHomeFragmentToQuizHomeFragment()
            findNavController().navigate(action)
        }

        binding.incGoExplore.btnStartExplore.setOnClickListener {
            if (checkPermissions(Permissions.CAMERA)) {
                navigateExploreScreen()
            } else {
                requestPermission(Permissions.CAMERA)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            Permissions.CAMERA.reqId -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    navigateExploreScreen()
                }
            }
        }
    }

    private fun navigateExploreScreen() {
        val action =
            HomeFragmentDirections.actionHomeFragmentToCameraFragment()
        findNavController().navigate(action)
    }
}
