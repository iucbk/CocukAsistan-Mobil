package com.iucbk_cocukasistan.custom_views.util

import android.content.res.Resources


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 26.03.2020 - 21:18          │
//└─────────────────────────────┘

val Int.dp: Float
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f)

val Float.dp: Float
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f)