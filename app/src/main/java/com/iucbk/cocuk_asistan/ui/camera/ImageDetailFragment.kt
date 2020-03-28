package com.iucbk.cocuk_asistan.ui.camera

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun initUI() {
        super.initUI()
        val activity = (requireActivity() as AppCompatActivity)
        activity.setSupportActionBar(binding.bottomAppBar)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                val action = ImageDetailFragmentDirections.actionImageDetailFragmentToHomeFragment()
                findNavController().navigate(action)
                true
            }
            R.id.knowledge -> {
                findNavController().popBackStack()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun initUserActionObservers() {
        super.initUserActionObservers()
        binding.fltShootPhotoAgain.setOnClickListener {
            val action =
                ImageDetailFragmentDirections.actionImageDetailFragmentToCameraFragment()
            findNavController().navigate(action)
        }
    }

    override fun initObservers() {
        super.initObservers()
        mainViewModel.imageFile.observe(viewLifecycleOwner, Observer {
            binding.imgShootedImage.setImageURI(it.toUri())
        })
    }
}
