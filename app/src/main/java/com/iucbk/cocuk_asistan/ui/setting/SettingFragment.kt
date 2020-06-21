package com.iucbk.cocuk_asistan.ui.setting

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.iucbk.cocuk_asistan.R
import com.iucbk.cocuk_asistan.common.BaseFragment
import com.iucbk.cocuk_asistan.data.model.PasswordResetDTO
import com.iucbk.cocuk_asistan.data.model.notif.AlarmModel
import com.iucbk.cocuk_asistan.databinding.FragmentSettingBinding
import com.iucbk.cocuk_asistan.di.AlarmScheduler
import com.iucbk.cocuk_asistan.ui.main.MainViewModel
import com.iucbk.cocuk_asistan.util.Status
import com.iucbk.cocuk_asistan.util.constant.NOTIFICATION_STATE
import com.iucbk.cocuk_asistan.util.constant.USER_TOKEN
import com.iucbk.cocuk_asistan.util.deleteSession
import com.iucbk.cocuk_asistan.util.extension.disableUserInteraction
import com.iucbk.cocuk_asistan.util.extension.enableUserInteraction
import com.iucbk.cocuk_asistan.util.extension.getData
import com.iucbk.cocuk_asistan.util.extension.getString
import com.iucbk.cocuk_asistan.util.extension.hide
import com.iucbk.cocuk_asistan.util.extension.log
import com.iucbk.cocuk_asistan.util.extension.putData
import com.iucbk.cocuk_asistan.util.extension.show
import com.iucbk.cocuk_asistan.util.extension.showSnackBar
import com.iucbk.cocuk_asistan.util.extension.showToast
import com.iucbk.cocuk_asistan.util.extension.viewBinding
import com.iucbk.cocuk_asistan.util.getErrorStringFromCode
import javax.inject.Inject

class SettingFragment : BaseFragment<SettingViewModel>(R.layout.fragment_setting) {

    override fun model() = SettingViewModel::class.java
    private val binding by viewBinding(FragmentSettingBinding::bind)
    private val mainViewModel: MainViewModel by activityViewModels()

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    @Inject
    lateinit var alarmScheduler: AlarmScheduler

    private lateinit var passwordResetBottomSheet: PasswordResetBottomSheet
    private lateinit var notificationSettingBottomSheet: NotificationSettingBottomSheet

    private var isLoading = false
    private var isNotificationEnable = false
    private var alarmModel: AlarmModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (isLoading) {
                        return
                    }
                    findNavController().popBackStack()
                }
            })
    }

    override fun initUI() {
        super.initUI()
        isNotificationEnable = sharedPreferences.getData(NOTIFICATION_STATE, false) as Boolean
    }

    override fun initUserActionObservers() {
        super.initUserActionObservers()

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.txtNotifications.setOnClickListener {
            openNotificationSettingDialog()
        }

        binding.txtChangePassword.setOnClickListener {
            openResetPasswordDialog()
        }

        binding.btnLogOut.setOnClickListener {
            binding.prgBar.show()
            logOut()
        }
    }

    private fun logOut() {
        if (sharedPreferences.deleteSession()) {
            mainViewModel.unAuthenticateUser()
            binding.prgBar.hide()
            findNavController()
                .navigate(
                    R.id.swipeUpScreen,
                    null,
                    NavOptions.Builder()
                        .setPopUpTo(R.id.homeFragment, true)
                        .setPopUpTo(R.id.settingFragment, true)
                        .build()
                )
        } else {
            binding.prgBar.hide()
            showSnackBar(getString(R.string.went_wrong))
        }
    }

    override fun initObservers() {
        super.initObservers()
        viewModel.newPasswordResult.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Status.SUCCESS -> {
                    enableUserInteraction()
                    isLoading = false
                    binding.prgBar.hide()
                    showSnackBar(getString(R.string.change_password_succes))
                }
                Status.ERROR -> {
                    enableUserInteraction()
                    isLoading = false
                    binding.prgBar.hide()
                    showSnackBar(getErrorStringFromCode(result.errorCode))
                    showToast(result.message)
                }
                Status.LOADING -> {
                    disableUserInteraction()
                    isLoading = true
                    binding.prgBar.show()
                }
            }
        })

        viewModel.getAlarmResult.observe(viewLifecycleOwner, Observer { alarmModels ->
            if (alarmModels.isNotEmpty()) {
                alarmModel = alarmModels.find {
                    it.token == sharedPreferences.getData(USER_TOKEN, "") as String
                }
                log(alarmModel.toString())
            }
        })
    }

    private fun openNotificationSettingDialog() {
        notificationSettingBottomSheet =
            NotificationSettingBottomSheet(alarmModel, isNotificationEnable,
                onCreateEvent = { bottomSheet, alarmModel, isNotificationEnabled ->
                    alarmModel.token = sharedPreferences.getData(USER_TOKEN, "") as String
                    saveNotificationSettingToDevice(alarmModel, isNotificationEnabled)
                    setNotification(alarmModel, isNotificationEnabled)
                    bottomSheet.dismiss()
                }, onCancelEvent = { bottomSheet ->
                    bottomSheet.dismiss()
                }
            ).also {
                it.show(parentFragmentManager, it.tag)
            }
    }

    private fun saveNotificationSettingToDevice(alarmModel: AlarmModel, isEnable: Boolean) {
        isNotificationEnable = isEnable
        sharedPreferences.putData(NOTIFICATION_STATE, isEnable)
        viewModel.setAlarmData(alarmModel)
    }

    private fun setNotification(alarmModel: AlarmModel, isEnable: Boolean) {
        if (isEnable) {
            alarmScheduler.scheduleAlarmsForReminder(alarmModel)
        } else {
            alarmScheduler.removeAlarmsForReminder(alarmModel.id)
        }
    }

    private fun openResetPasswordDialog() {
        passwordResetBottomSheet = PasswordResetBottomSheet(
            onCreateEvent = { bottomSheet, isAvailable ->
                if (isAvailable) {
                    val passwordResetDTO = PasswordResetDTO(
                        bottomSheet.binding.txtNewPassword.getString()
                    )
                    viewModel.setNewPasswordDate(passwordResetDTO)
                    bottomSheet.dismiss()
                }
            }, onCancelEvent = { bottomSheet ->
                bottomSheet.dismiss()
            }).also {
            it.show(parentFragmentManager, it.tag)
        }
    }
}