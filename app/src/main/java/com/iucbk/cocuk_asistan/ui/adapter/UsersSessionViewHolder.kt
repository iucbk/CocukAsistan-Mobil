package com.iucbk.cocuk_asistan.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.iucbk.cocuk_asistan.data.db.entity.UserSession
import com.iucbk.cocuk_asistan.databinding.ItemUserSessionBinding


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 03.04.2020 - 13:11          │
//└─────────────────────────────┘

class UsersSessionViewHolder(
    private val binding: ItemUserSessionBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(userSession: UserSession, onClickListener: (UserSession) -> Unit) {
        binding.btnUseAnotherAccount.text = userSession.email

        binding.btnUseAnotherAccount.setOnClickListener {
            onClickListener(userSession)
        }
    }
}