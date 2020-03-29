package com.iucbk.cocuk_asistan.ui.`object`.item

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.iucbk.cocuk_asistan.R
import com.iucbk.cocuk_asistan.databinding.FragmentObjectImageBinding
import com.iucbk.cocuk_asistan.ui.`object`.ImageDetailFragment
import com.iucbk.cocuk_asistan.ui.`object`.ImageDetailFragmentDirections
import com.iucbk.cocuk_asistan.util.extension.viewBinding

class ObjectImageFragment : Fragment(R.layout.fragment_object_image) {

    private val binding by viewBinding(FragmentObjectImageBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = (requireActivity() as AppCompatActivity)
        activity.setSupportActionBar(binding.bottomAppBar)

        binding.fltShootPhotoAgain.setOnClickListener {
            val action =
                ImageDetailFragmentDirections.actionImageDetailFragmentToCameraFragment()
            findNavController().navigate(action)
        }

        (requireParentFragment() as ImageDetailFragment).mainViewModel.imageFile.observe(
            viewLifecycleOwner,
            Observer {
                binding.imgShootedImage.setImageURI(it.toUri())
            })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                val action =
                    ImageDetailFragmentDirections.actionImageDetailFragmentToHomeFragment()
                findNavController().navigate(action)
                true
            }
            R.id.knowledge -> {
                (requireParentFragment() as ImageDetailFragment).binding
                    .vpObjectDetail.setCurrentItem(
                        1,
                        true
                    )
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            ObjectImageFragment()
    }
}
