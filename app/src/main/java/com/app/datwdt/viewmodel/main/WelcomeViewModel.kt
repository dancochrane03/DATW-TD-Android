package com.app.datwdt.viewmodel.main

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.datwdt.R
import com.app.datwdt.constants.Constants
import com.app.datwdt.data.main.Resource
import com.app.datwdt.data.model.main.GetUserResponse
import com.app.datwdt.data.repository.MainRepository
import com.app.datwdt.databinding.FragmentLoginBinding
import com.app.datwdt.databinding.FragmentSecurityCodeCheckBinding
import com.app.datwdt.databinding.FragmentWelcomeBinding
import com.app.datwdt.util.FormValidation
import com.app.datwdt.util.Preferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.HashMap
import javax.inject.Inject


class WelcomeViewModel @Inject
constructor(var mainRepository: MainRepository) : ViewModel() {

    lateinit var activity: Activity
    lateinit var mBinding: FragmentWelcomeBinding

    @Inject
    lateinit var preferences: Preferences

    //API
    private val compositeDisposable = CompositeDisposable()


    var _code: MutableLiveData<String> = MutableLiveData()

    init {
//        _emailAddress.value = "shaikhvahida33@gmail.com"
//        _password.value = "12345678"

    }

    fun initialize(activity: Activity, mBinding: FragmentWelcomeBinding) {
        this.activity = activity
        this.mBinding = mBinding
        preferences=Preferences(activity)
    }

    //LIVE DATA
    private val userliveData = MutableLiveData<Resource<GetUserResponse>>()

    fun getUserliveData(): MutableLiveData<Resource<GetUserResponse>> {
        return userliveData
    }


    fun callGetUserApi() {
        val params = HashMap<String, String>()
        params[Constants.entity_id] = preferences.getString(Constants.entity_id).toString()

        compositeDisposable.add(mainRepository.getUserAPI(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { disposable: Disposable ->
//                userliveData.setValue(
//                    Resource.loading()
//                )
            }
            .subscribe(
                { response: GetUserResponse? ->
                    userliveData.setValue(
                        Resource.success(response)
                    )
                },
                { throwable: Throwable? ->
                    userliveData.setValue(
                        Resource.error(throwable!!)
                    )
                }
            ))

    }

}

