package com.app.datwdt.viewmodel.main

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.datwdt.R
import com.app.datwdt.constants.Constants
import com.app.datwdt.data.main.Resource
import com.app.datwdt.data.model.main.CreateGroupResponse
import com.app.datwdt.data.model.main.GetUserResponse
import com.app.datwdt.data.repository.MainRepository
import com.app.datwdt.databinding.FragmentCreateNewGroupBinding
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


class CreateNewGroupViewModel @Inject
constructor(var mainRepository: MainRepository) : ViewModel() {

    lateinit var activity: Activity
    lateinit var mBinding: FragmentCreateNewGroupBinding

    @Inject
    lateinit var preferences: Preferences

    //API
    private val compositeDisposable = CompositeDisposable()


    var _groupname: MutableLiveData<String> = MutableLiveData()

    init {
//        _emailAddress.value = "shaikhvahida33@gmail.com"
//        _password.value = "12345678"

    }

    fun initialize(activity: Activity, mBinding: FragmentCreateNewGroupBinding) {
        this.activity = activity
        this.mBinding = mBinding
        preferences=Preferences(activity)
    }

    private val createGroupliveData = MutableLiveData<Resource<CreateGroupResponse>>()

    fun createGroupliveData(): MutableLiveData<Resource<CreateGroupResponse>> {
        return createGroupliveData
    }

    fun createGroupApi() {
        val params = HashMap<String, String>()
        params[Constants.group_nm] = _groupname.value.toString()

        if(preferences.getString(Constants.user_role).equals("admin",true)){
            params[Constants.entity_id] = preferences.getString(Constants.entity_id).toString()
        }

        compositeDisposable.add(mainRepository.createGroupAPI(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { disposable: Disposable ->
                createGroupliveData.setValue(
                    Resource.loading()
                )
            }
            .subscribe(
                { response: CreateGroupResponse? ->
                    createGroupliveData.setValue(
                        Resource.success(response)
                    )
                },
                { throwable: Throwable? ->
                    createGroupliveData.setValue(
                        Resource.error(throwable!!)
                    )
                }
            ))

    }



}

