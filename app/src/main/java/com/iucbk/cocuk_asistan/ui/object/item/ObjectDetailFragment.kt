package com.iucbk.cocuk_asistan.ui.`object`.item

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.iucbk.cocuk_asistan.R
import com.iucbk.cocuk_asistan.databinding.FragmentObjectDetailBinding
import com.iucbk.cocuk_asistan.ui.`object`.ImageDetailFragment
import com.iucbk.cocuk_asistan.ui.adapter.ObjectDetailAdapter
import com.iucbk.cocuk_asistan.util.delegate.AutoClearedValue
import com.iucbk.cocuk_asistan.util.extension.viewBinding


class ObjectDetailFragment : Fragment(R.layout.fragment_object_detail) {

    private val binding by viewBinding(FragmentObjectDetailBinding::bind)

    private var adapter by AutoClearedValue<ObjectDetailAdapter>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            (requireParentFragment() as ImageDetailFragment).binding.vpObjectDetail.setCurrentItem(
                0,
                true
            )
        }

        adapter = ObjectDetailAdapter().also {
            binding.recycObjectData.adapter = it
        }

        //Mock data
        val list = mutableListOf<Pair<String, String>>().apply {
            add(Pair("Renk", "Sarı,Beyaz,Gri"))
            add(Pair("Kanak", "2"))
            add(Pair("Tür", "Kuş"))
            add(Pair("Kökeni", "Avusturalya"))
        }

        adapter.submitList(list)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ObjectDetailFragment()
    }
}
