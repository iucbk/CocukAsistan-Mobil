package com.iucbk.cocuk_asistan.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.iucbk.cocuk_asistan.databinding.ItemObjectDetailBinding
import com.iucbk.cocuk_asistan.util.GenericDiffUtil


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 29.03.2020 - 01:35          │
//└─────────────────────────────┘

class ObjectDetailAdapter :
    ListAdapter<Pair<String, String>, ObjectDetailViewHolder>(GenericDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ObjectDetailViewHolder {
        val binding = ItemObjectDetailBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ObjectDetailViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: ObjectDetailViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}