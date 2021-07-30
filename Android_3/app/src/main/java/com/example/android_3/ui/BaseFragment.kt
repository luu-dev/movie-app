package com.example.android_3.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import com.example.android_3.base.BaseViewModel
import com.example.android_3.ui.login.LoginRegisterViewModel


open abstract class BaseFragment1<BD:ViewDataBinding,VM: BaseViewModel>: Fragment() {
    protected lateinit var binding: BD
    protected lateinit var viewModel:VM

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       binding = DataBindingUtil.inflate(inflater!!,getLayoutID(),container,false)
        return  binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(getViewModelClass()!!)


    }
    protected abstract fun getLayoutID(): Int
    protected abstract fun getViewModelClass(): Class<VM>


}