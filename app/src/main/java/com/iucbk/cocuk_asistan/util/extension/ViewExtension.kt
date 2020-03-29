package com.iucbk.cocuk_asistan.util.extension

import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.iucbk.cocuk_asistan.util.PermissionUtil
import com.iucbk.cocuk_asistan.util.constant.CAMERA_IMAGE_ID
import com.iucbk.cocuk_asistan.util.delegate.FragmentViewBindingDelegate


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 02.03.2020 - 12:13          │
//└─────────────────────────────┘

fun Fragment.showSnackBar(text: String?) {
    text?.let { message ->
        Snackbar.make(view!!, message, Snackbar.LENGTH_SHORT).show()
    }
}

fun Fragment.showToast(text: String?) {
    text?.let { message ->
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun <T : ViewBinding> Fragment.viewBinding(viewBindingFactory: (View) -> T) =
    FragmentViewBindingDelegate(
        this,
        viewBindingFactory
    )

fun Fragment.requestPermission(permList: Array<String>, permID: Int) {
    PermissionUtil.askPermissions(this, permList, permID)
}

fun Fragment.checkPermissions(permList: Array<String>, permID: Int): Boolean {
    return when (permID) {
        CAMERA_IMAGE_ID -> {
            if (PermissionUtil.filterNotGrantedPermissions(permList, context!!).isEmpty()) {
                true
            } else {
                requestPermission(permList, CAMERA_IMAGE_ID)
                false
            }
        }
        else -> false
    }
}

fun Fragment.hasPerm(permList: Array<String>): Boolean {
    return PermissionUtil.hasPermissions(requireContext(), permList)
}

const val ANIMATION_FAST_MILLIS = 50L
const val ANIMATION_SLOW_MILLIS = 100L

fun View.simulateClick(delay: Long = ANIMATION_FAST_MILLIS) {
    performClick()
    isPressed = true
    invalidate()
    postDelayed({
        invalidate()
        isPressed = false
    }, delay)
}

