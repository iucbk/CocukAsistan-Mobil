package com.iucbk.cocuk_asistan.ui.user.home

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.iucbk.cocuk_asistan.R
import com.iucbk.cocuk_asistan.databinding.FragmentHomeBinding
import com.iucbk.cocuk_asistan.util.constant.CAMERA_IMAGE_ID
import com.iucbk.cocuk_asistan.util.constant.UPLOAD_IMAGE_PERMISSION_CAMERA
import com.iucbk.cocuk_asistan.util.extension.checkPermissions
import com.iucbk.cocuk_asistan.util.extension.requestPermission
import com.iucbk.cocuk_asistan.util.extension.showToast
import com.iucbk.cocuk_asistan.util.extension.viewBinding

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleBackPress()
        initUserActions()
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

    private fun initUserActions() {
        binding.incGoQuiz.btnSolveQuiz.setOnClickListener {
            val action =
                HomeFragmentDirections.actionHomeFragmentToQuizHomeFragment()
            findNavController().navigate(action)
        }

        binding.incGoExplore.btnStartExplore.setOnClickListener {
            if (checkPermissions(UPLOAD_IMAGE_PERMISSION_CAMERA, CAMERA_IMAGE_ID)) {
                navigateExploreScreen()
            } else {
                requestPermission(UPLOAD_IMAGE_PERMISSION_CAMERA, CAMERA_IMAGE_ID)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            CAMERA_IMAGE_ID -> {
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
