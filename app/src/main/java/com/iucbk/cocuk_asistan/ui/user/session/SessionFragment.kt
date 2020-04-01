package com.iucbk.cocuk_asistan.ui.user.session

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.iucbk.cocuk_asistan.R
import com.iucbk.cocuk_asistan.common.BaseFragment
import com.iucbk.cocuk_asistan.databinding.FragmentSessionBinding
import com.iucbk.cocuk_asistan.util.extension.viewBinding

/**
 * A simple [Fragment] subclass.
 */
class SessionFragment : BaseFragment<SessionViewModel>(R.layout.fragment_session) {

    override fun model(): Any {
        return SessionViewModel::class.java
    }

    private val binding by viewBinding(FragmentSessionBinding::bind)

    override fun initUserActionObservers() {
        super.initUserActionObservers()

        viewModel.usersSession.observe(viewLifecycleOwner, Observer {
            Log.e("Result : ", it.toString())
        })
    }
}
