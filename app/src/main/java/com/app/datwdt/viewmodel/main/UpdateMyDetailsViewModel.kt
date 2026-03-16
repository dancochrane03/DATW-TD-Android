package com.app.datwdt.viewmodel.main

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.datwdt.constants.Constants
import com.app.datwdt.data.main.Resource
import com.app.datwdt.data.model.auth.UserResponse
import com.app.datwdt.data.model.main.GetUserResponse
import com.app.datwdt.databinding.*
import com.app.datwdt.data.repository.MainRepository
import com.app.datwdt.util.Preferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.HashMap
import javax.inject.Inject


class UpdateMyDetailsViewModel @Inject
constructor(var mainRepository: MainRepository) : ViewModel() {

    lateinit var activity: Activity
    lateinit var mBinding: FragmentUpdateMyDetailsBinding

    @Inject
    lateinit var preferences:Preferences


    //API
    private val compositeDisposable = CompositeDisposable()


    var _emailAddress: MutableLiveData<String> = MutableLiveData()
    var _password: MutableLiveData<String> = MutableLiveData()
    var _address: MutableLiveData<String> = MutableLiveData()
    var _phoneNumber: MutableLiveData<String> = MutableLiveData()
    var _dob: MutableLiveData<String> = MutableLiveData()
    var _ffnumber: MutableLiveData<String> = MutableLiveData()

    init {
    }

    fun initialize(activity: Activity, mBinding: FragmentUpdateMyDetailsBinding) {
        this.activity = activity
        this.mBinding = mBinding
        preferences=Preferences(activity)
    }

    //LIVE DATA
    private val userliveData = MutableLiveData<Resource<GetUserResponse>>()

    fun getUserliveData(): MutableLiveData<Resource<GetUserResponse>> {
        return userliveData
    }

    private val updateUserliveData = MutableLiveData<Resource<GetUserResponse>>()

    fun getUpdateUserliveData(): MutableLiveData<Resource<GetUserResponse>> {
        return updateUserliveData
    }

    fun callGetUserApi() {
        val params = HashMap<String, String>()
        params[Constants.entity_id] = preferences.getString(Constants.entity_id).toString()

        compositeDisposable.add(mainRepository.getUserAPI(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { disposable: Disposable ->
                userliveData.setValue(
                    Resource.loading()
                )
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

    fun updateUserDetailsApi() {
        val params = HashMap<String, String>()
        params[Constants.email] = _emailAddress.value.toString()
        params[Constants.user_pass] = _password.value.toString()
        params[Constants.entity_id] = preferences.getString(Constants.entity_id).toString()
        params[Constants.phone_number] = _phoneNumber.value.toString()
        params[Constants.qf_ff_number] = _ffnumber.value.toString()
        params[Constants.dob] = _dob.value.toString()
        params[Constants.address] = _address.value.toString()

        compositeDisposable.add(mainRepository.updateUserAPI(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { disposable: Disposable ->
                updateUserliveData.setValue(
                    Resource.loading()
                )
            }
            .subscribe(
                { response: GetUserResponse? ->
                    updateUserliveData.setValue(
                        Resource.success(response)
                    )
                },
                { throwable: Throwable? ->
                    updateUserliveData.setValue(
                        Resource.error(throwable!!)
                    )
                }
            ))

    }


}

