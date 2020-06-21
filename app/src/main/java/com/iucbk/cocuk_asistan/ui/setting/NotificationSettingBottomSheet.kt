package com.iucbk.cocuk_asistan.ui.setting

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.core.view.get
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.iucbk.cocuk_asistan.R
import com.iucbk.cocuk_asistan.data.model.notif.AlarmModel
import com.iucbk.cocuk_asistan.databinding.BottomSheetNotificationSettingBinding
import com.iucbk.cocuk_asistan.util.extension.disableEnableControls

// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 15.06.2020 - 16:35          │
//└─────────────────────────────┘

class NotificationSettingBottomSheet(
    private val alarmModel: AlarmModel?,
    private var isNotificationEnable: Boolean,
    private val onCreateEvent: (NotificationSettingBottomSheet, AlarmModel, Boolean) -> Unit,
    private val onCancelEvent: (NotificationSettingBottomSheet) -> Unit
) : BottomSheetDialogFragment() {

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<*>
    internal val binding get() = _binding!!
    private var _binding: BottomSheetNotificationSettingBinding? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = View.inflate(requireContext(), R.layout.bottom_sheet_notification_setting, null)
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        view?.let {
            _binding = BottomSheetNotificationSettingBinding.bind(it)
            bottomSheet.setContentView(it)
            bottomSheetBehavior = BottomSheetBehavior.from(it.parent as View)
        }
        setUIData()
        bottomSheetBehavior.let {
            it.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO
            it.addBottomSheetCallback(
                object : BottomSheetBehavior.BottomSheetCallback() {
                    override fun onStateChanged(view: View, i: Int) {
                        if (BottomSheetBehavior.STATE_HIDDEN == i) {
                            dismiss()
                        } else if (BottomSheetBehavior.STATE_DRAGGING == i) {
                            it.state = BottomSheetBehavior.STATE_EXPANDED
                        }
                    }

                    override fun onSlide(view: View, v: Float) {}
                })
        }
        initUserActions()
        return bottomSheet
    }

    private fun setUIData() {
        binding.timePicker.setIs24HourView(true)
        binding.smIsOpenNotification.isChecked = isNotificationEnable
        changeNotificationAreaState(isNotificationEnable)
        alarmModel?.let { model ->
            binding.timePicker.hour = model.hour
            binding.timePicker.minute = model.min
            checkChips(model.days)
        }
    }

    private fun changeNotificationAreaState(state: Boolean) {
        if (isNotificationEnable) {
            binding.lytNotificationArea.disableEnableControls(state)
            binding.lytNotificationArea.alpha = 1f
        } else {
            binding.lytNotificationArea.disableEnableControls(state)
            binding.lytNotificationArea.alpha = 0.5f
        }
    }


    private fun initUserActions() {
        binding.smIsOpenNotification.setOnCheckedChangeListener { buttonView, isChecked ->
            isNotificationEnable = isChecked
            changeNotificationAreaState(isNotificationEnable)
        }

        binding.btnCancel.setOnClickListener {
            onCancelEvent.invoke(this)
        }

        binding.btnSetNotifications.setOnClickListener {
            val alarmModel = AlarmModel(
                hour = binding.timePicker.hour,
                min = binding.timePicker.minute,
                days = getCheckedChips()
            )
            onCreateEvent.invoke(this, alarmModel, isNotificationEnable)
        }
    }

    private fun getCheckedChips(): List<String> {
        val checkedList = mutableListOf<String>()
        repeat(7) {
            if ((binding.gchpDays[it] as Chip).isChecked) {
                checkedList.add((binding.gchpDays[it] as Chip).tag.toString())
            }
        }
        return checkedList
    }

    private fun checkChips(list: List<String>) {
        repeat(7) {
            if (list.contains((binding.gchpDays[it] as Chip).tag)) {
                (binding.gchpDays[it] as Chip).isChecked = true
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onStart() {
        super.onStart()
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }
}
