package com.iucbk.cocuk_asistan.ui.camera

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.iucbk.cocuk_asistan.R
import com.iucbk.cocuk_asistan.common.BaseFragment
import com.iucbk.cocuk_asistan.databinding.FragmentImageDetailBinding
import com.iucbk.cocuk_asistan.ui.main.MainViewModel
import com.iucbk.cocuk_asistan.util.extension.viewBinding

/**
 * A simple [Fragment] subclass.
 */
class ImageDetailFragment : BaseFragment<ImageDetailViewModel>(R.layout.fragment_image_detail) {

    override fun model(): Any {
        return ImageDetailViewModel::class.java
    }

    private val mainViewModel: MainViewModel by activityViewModels()

    private val binding by viewBinding(FragmentImageDetailBinding::bind)

    override fun initObservers() {
        super.initObservers()
        mainViewModel.imageFile.observe(viewLifecycleOwner, Observer {
            binding.text.text = it.absoluteFile.absolutePath
        })
    }
}
