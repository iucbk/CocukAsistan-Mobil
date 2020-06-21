package com.iucbk_cocukasistan.custom_views.buttons

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import com.google.android.material.chip.Chip
import com.iucbk_cocukasistan.custom_views.R


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 15.06.2020 - 15:04          │
//└─────────────────────────────┘

class CircleChip @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet,
    defValue: Int = 0
) : Chip(context, attributeSet) {

    init {
        if (this.isSelected) {
            this.setChipBackgroundColorResource(R.color.selectedChipColor)
            this.setTextColor(Color.WHITE)
        } else {
            this.setChipBackgroundColorResource(R.color.unselectedChipColor)
            this.setTextColor(Color.parseColor("#e78fb3"))
        }
    }
}