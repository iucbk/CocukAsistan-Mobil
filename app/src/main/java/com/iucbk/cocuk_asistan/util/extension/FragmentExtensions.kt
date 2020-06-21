package com.iucbk.cocuk_asistan.util.extension

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.iucbk.cocuk_asistan.enums.Permissions
import com.iucbk.cocuk_asistan.util.PermissionUtil
import com.iucbk.cocuk_asistan.util.delegate.FragmentViewBindingDelegate


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 18.06.2020 - 18:30          │
//└─────────────────────────────┘


fun <T : ViewBinding> Fragment.viewBinding(viewBindingFactory: (View) -> T) =
    FragmentViewBindingDelegate(
        this,
        viewBindingFactory
    )

fun Fragment.requestPermission(perm: Permissions) {
    PermissionUtil.askPermissions(this, perm.getPermissionArray(), perm.reqId)
}

fun Fragment.checkPermissions(perm: Permissions): Boolean {
    return when (perm) {
        Permissions.CAMERA -> {
            if (PermissionUtil.filterNotGrantedPermissions(
                    perm.getPermissionArray(),
                    requireContext()
                )
                    .isEmpty()
            ) {
                true
            } else {
                requestPermission(perm)
                false
            }
        }
    }
}

fun Fragment.hasPerm(perm: Permissions) =
    PermissionUtil.hasPermissions(requireContext(), perm.getPermissionArray())


fun Fragment.hideKeyboard() {
    this.let { requireActivity().hideKeyboard() }
}

fun Activity.hideKeyboard() {
    this.currentFocus?.let {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(it.windowToken, 0)
        return
    }
    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
}

fun Fragment.disableUserInteraction() {
    requireActivity()
        .window
        .setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
}

fun Fragment.enableUserInteraction() {
    requireActivity()
        .window
        .clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
}

fun Fragment.log(message: String?) {
    message?.let { it -> Log.e(this.javaClass.name, it) }
}

fun Fragment.showSnackBar(text: String?) {
    text?.let { message ->
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
    }
}

fun Fragment.showToast(text: String?) {
    text?.let { message ->
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}