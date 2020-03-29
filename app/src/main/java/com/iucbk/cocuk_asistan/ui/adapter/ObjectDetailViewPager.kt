package com.iucbk.cocuk_asistan.ui.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.iucbk.cocuk_asistan.ui.`object`.item.ObjectDetailFragment
import com.iucbk.cocuk_asistan.ui.`object`.item.ObjectImageFragment


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 29.03.2020 - 00:24          │
//└─────────────────────────────┘

class ObjectDetailViewPager(
    fragment: Fragment
) : FragmentStateAdapter(
    fragment
) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                ObjectImageFragment.newInstance()
            }
            1 -> {
                ObjectDetailFragment.newInstance()
            }
            else -> ObjectImageFragment.newInstance()
        }
    }
}