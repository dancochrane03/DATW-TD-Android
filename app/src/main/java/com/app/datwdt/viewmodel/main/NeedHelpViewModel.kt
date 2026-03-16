package com.app.datwdt.viewmodel.main

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.datwdt.R
import com.app.datwdt.databinding.*
import com.app.datwdt.util.FormValidation
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


class NeedHelpViewModel @Inject
constructor() : ViewModel() {

    lateinit var activity: Activity
    lateinit var mBinding: FragmentNeedHelpBinding


    //API
    private val compositeDisposable = CompositeDisposable()


    var _groupname: MutableLiveData<String> = MutableLiveData()

    init {
//        _emailAddress.value = "shaikhvahida33@gmail.com"
//        _password.value = "12345678"

    }

    fun initialize(activity: Activity, mBinding: FragmentNeedHelpBinding) {
        this.activity = activity
        this.mBinding = mBinding
    }




}

