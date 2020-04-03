package com.iucbk.cocuk_asistan.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.iucbk.cocuk_asistan.data.db.entity.UserSession
import com.iucbk.cocuk_asistan.databinding.ItemUserSessionBinding
import com.iucbk.cocuk_asistan.util.GenericDiffUtil


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 03.04.2020 - 13:10          │
//└─────────────────────────────┘

class UsersSessionAdapter(
    private val setOnClickListener: (UserSession) -> Unit
) : ListAdapter<UserSession, UsersSessionViewHolder>(GenericDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersSessionViewHolder {
        val binding = ItemUserSessionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UsersSessionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsersSessionViewHolder, position: Int) {
        holder.bind(getItem(position), setOnClickListener)
    }
}