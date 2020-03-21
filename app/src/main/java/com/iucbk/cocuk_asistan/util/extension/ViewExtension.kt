package com.iucbk.cocuk_asistan.util.extension

import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
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