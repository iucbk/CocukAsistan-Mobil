package com.iucbk.cocuk_asistan.util.extension

import android.content.res.Resources
import android.view.View
import android.view.ViewGroup


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 02.03.2020 - 12:13          │
//└─────────────────────────────┘

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun ViewGroup.disableEnableControls(enable: Boolean) {
    for (i in 0 until this.childCount) {
        val child = this.getChildAt(i)
        child.isEnabled = enable
        if (child is ViewGroup) {
            child.disableEnableControls(enable)
        }
    }
}

val Int.dp: Float
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f)

val Float.dp: Float
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f)