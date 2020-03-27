package com.iucbk_cocukasistan.custom_views.buttons

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.button.MaterialButton
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.ShapeAppearanceModel
import com.iucbk_cocukasistan.custom_views.util.dp


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 26.03.2020 - 21:22          │
//└─────────────────────────────┘

class SwipeUpSignUpButton @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet?,
    defValue: Int = 0
) : MaterialButton(
    context,
    attributeSet,
    defValue
) {
    init {
        val shapeAppearanceModel = ShapeAppearanceModel()
            .toBuilder()
            .setBottomRightCorner(CornerFamily.ROUNDED, 30.dp)
            .setTopRightCorner(CornerFamily.ROUNDED, 30.dp)
            .build()

        this.shapeAppearanceModel = shapeAppearanceModel
    }
}