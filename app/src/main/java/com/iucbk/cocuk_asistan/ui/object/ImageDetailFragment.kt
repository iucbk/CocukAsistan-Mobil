package com.iucbk.cocuk_asistan.ui.`object`

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.iucbk.cocuk_asistan.R
import com.iucbk.cocuk_asistan.common.BaseFragment
import com.iucbk.cocuk_asistan.databinding.FragmentImageDetailBinding
import com.iucbk.cocuk_asistan.ui.adapter.ObjectDetailViewPager
import com.iucbk.cocuk_asistan.ui.main.MainViewModel
import com.iucbk.cocuk_asistan.util.delegate.AutoClearedValue
import com.iucbk.cocuk_asistan.util.extension.viewBinding

/**
 * A simple [Fragment] subclass.
 */
class ImageDetailFragment : BaseFragment<ImageDetailViewModel>(R.layout.fragment_image_detail) {

    override fun model(): Any {
        return ImageDetailViewModel::class.java
    }

    internal val mainViewModel: MainViewModel by activityViewModels()

    private var vpAdapter by AutoClearedValue<ObjectDetailViewPager>()

    internal val binding by viewBinding(FragmentImageDetailBinding::bind)

    override fun initUI() {
        super.initUI()
        vpAdapter = ObjectDetailViewPager(this)
        binding.vpObjectDetail.adapter = vpAdapter
    }

}
