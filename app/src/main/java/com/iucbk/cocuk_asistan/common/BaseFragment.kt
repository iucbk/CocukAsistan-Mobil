package com.iucbk.cocuk_asistan.common

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.iucbk.cocuk_asistan.di.ViewModelFactory
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 02.03.2020 - 12:10          │
//└─────────────────────────────┘

abstract class BaseFragment<VM : ViewModel>(@LayoutRes layoutRes: Int) :
    Fragment(layoutRes), HasAndroidInjector {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    abstract fun model(): Any

    internal lateinit var viewModel: VM
        private set

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    open fun initUserActionObservers() {}

    open fun initObservers() {}

    open fun initUI() {}

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(model() as Class<VM>)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initUserActionObservers()
        initObservers()
    }
}