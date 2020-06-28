package com.iucbk.cocuk_asistan.ui.user.home

import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.iucbk.cocuk_asistan.R
import com.iucbk.cocuk_asistan.common.BaseFragment
import com.iucbk.cocuk_asistan.databinding.FragmentHomeBinding
import com.iucbk.cocuk_asistan.enums.Permissions
import com.iucbk.cocuk_asistan.util.constant.USER_FULL_NAME
import com.iucbk.cocuk_asistan.util.extension.getData
import com.iucbk.cocuk_asistan.util.extension.showSnackBar
import com.iucbk.cocuk_asistan.util.extension.showToast
import com.iucbk.cocuk_asistan.util.extension.viewBinding
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : BaseFragment<HomeViewModel>(R.layout.fragment_home) {

    override fun model() = HomeViewModel::class.java

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val userName by lazy {
        (sharedPreferences.getData(USER_FULL_NAME, "") as String).substringBefore(" ")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAnalytics.setCurrentScreen(requireActivity(), this.javaClass.name, null)
    }

    override fun initUI() {
        super.initUI()
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

        binding.imgSetting.setOnClickListener {
            navigateToSettingFragment()
        }

        binding.incGoQuiz.btnSolveQuiz.setOnClickListener {
            val action =
                HomeFragmentDirections.actionHomeFragmentToQuizHomeFragment()
            findNavController().navigate(action)
        }

        binding.incGoExplore.btnStartExplore.setOnClickListener {
            navigateToCameraFragment()
        }
    }

    private fun navigateToSettingFragment() {
        val action = HomeFragmentDirections.actionHomeFragmentToSettingFragment()
        findNavController().navigate(action)
    }

    private fun navigateToCameraFragment() {
        showSnackBar(getString(R.string.will_be_add))
//        if (checkPermissions(Permissions.CAMERA)) {
//            navigateExploreScreen()
//        } else {
//            requestPermission(Permissions.CAMERA)
//        }
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
