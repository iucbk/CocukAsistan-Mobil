package com.iucbk.cocuk_asistan.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.iucbk.cocuk_asistan.databinding.ItemObjectDetailBinding


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 29.03.2020 - 01:36          │
//└─────────────────────────────┘

class ObjectDetailViewHolder(
    private val binding: ItemObjectDetailBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: Pair<String, String>) {
        binding.txtTitle.text = model.first
        binding.txtDesc.text = model.second
    }

}