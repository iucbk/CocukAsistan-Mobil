package com.iucbk.cocuk_asistan.ui.setting

import android.app.Dialog
import android.os.Bundle
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.iucbk.cocuk_asistan.R
import com.iucbk.cocuk_asistan.databinding.BottomSheetPasswordChangeBinding
import com.iucbk.cocuk_asistan.util.extension.getString
import com.iucbk.cocuk_asistan.util.extension.isLengthValid
import com.iucbk.cocuk_asistan.util.extension.showToast
import com.iucbk.cocuk_asistan.util.extension.userFilledAllEntries


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 15.06.2020 - 14:14          │
//└─────────────────────────────┘

class PasswordResetBottomSheet(
    private val onCreateEvent: (PasswordResetBottomSheet, Boolean) -> Unit,
    private val onCancelEvent: (PasswordResetBottomSheet) -> Unit
) : BottomSheetDialogFragment() {


    private lateinit var bottomSheetBehavior: BottomSheetBehavior<*>
    private var _binding: BottomSheetPasswordChangeBinding? = null
    internal val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = View.inflate(requireContext(), R.layout.bottom_sheet_password_change, null)
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        view?.let {
            _binding = BottomSheetPasswordChangeBinding.bind(it)
            bottomSheet.setContentView(it)
            bottomSheetBehavior = BottomSheetBehavior.from(it.parent as View)
        }

        bottomSheetBehavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO
        bottomSheetBehavior.addBottomSheetCallback(
            object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(view: View, i: Int) {
                    if (BottomSheetBehavior.STATE_HIDDEN == i) {
                        dismiss()
                    } else if (BottomSheetBehavior.STATE_DRAGGING == i) {
                        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                    }
                }

                override fun onSlide(view: View, v: Float) {}
            })

        binding.btnCancel.setOnClickListener {
            onCancelEvent.invoke(this)
        }
        binding.btnChangePassword.setOnClickListener {
            val allFields = listOf(
                binding.txtOldPassword.getString(),
                binding.txtNewPassword.getString(),
                binding.txtPasswordConfirmation.getString()
            )
            if (!allFields.userFilledAllEntries()) {
                showToast(getString(R.string.fill_all_blank))
                onCreateEvent.invoke(this, false)
                return@setOnClickListener
            }
            if (binding.txtNewPassword.getString() != binding.txtPasswordConfirmation.getString()) {
                showToast(getString(R.string.passwords_does_not_match))
                onCreateEvent.invoke(this, false)
                return@setOnClickListener
            }
            if (!isLengthValid(binding.txtNewPassword.getString(), 6)) {
                showToast(getString(R.string.short_password))
                onCreateEvent.invoke(this, false)
                return@setOnClickListener
            }
            onCreateEvent.invoke(this, true)
        }
        return bottomSheet
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