package com.app.datwdt.viewmodel.auth

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.datwdt.R
import com.app.datwdt.constants.Constants
import com.app.datwdt.data.main.Resource
import com.app.datwdt.data.model.CommonResponse
import com.app.datwdt.data.model.auth.UserResponse
import com.app.datwdt.databinding.FragmentSecurityCodeCheckBinding
import com.app.datwdt.util.FormValidation
import com.app.datwdt.data.repository.AuthRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class SecurityCodeCheckViewModel @Inject
constructor(var authRepository: AuthRepository) : ViewModel() {

    lateinit var activity: Activity
    lateinit var mBinding: FragmentSecurityCodeCheckBinding


    //API
    private val compositeDisposable = CompositeDisposable()


    var _otp_code: MutableLiveData<String> = MutableLiveData()

    init {
//        _emailAddress.value = "shaikhvahida33@gmail.com"
//        _password.value = "12345678"

    }

    fun initialize(activity: Activity, mBinding: FragmentSecurityCodeCheckBinding) {
        this.activity = activity
        this.mBinding = mBinding
    }


    //LIVE DATA
    private val userliveData = MutableLiveData<Resource<UserResponse>>()

    fun getUserLiveData(): MutableLiveData<Resource<UserResponse>> {
        return userliveData
    }

    private val resendOtpliveData = MutableLiveData<Resource<CommonResponse>>()

    fun getResendOtpLiveData(): MutableLiveData<Resource<CommonResponse>> {
        return resendOtpliveData
    }

    fun callVerifyOtpApi() {
        if (isValidInput()) {
            val params = HashMap<String, String>()
            params[Constants.otp_code] = _otp_code.value.toString()


            compositeDisposable.add(authRepository.verifyOtpAPI(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { disposable: Disposable ->
                    userliveData.setValue(
                        Resource.loading()
                    )
                }
                .subscribe(
                    { response: UserResponse? ->
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

    private fun isValidInput(): Boolean {

        val validation = FormValidation(activity)
        return if (!validation.isEmpty(
                _otp_code.value,
                activity.getResources().getString(R.string.otp_code_empty)
            )
        ) {
            mBinding.edtEnterCode.requestFocus()
            false
        } else {
            true
        }

    }

    fun callResendOtpApi() {

        val params = HashMap<String, String>()


        compositeDisposable.add(authRepository.resendOtpAPI(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { disposable: Disposable ->
                resendOtpliveData.setValue(
                    Resource.loading()
                )
            }
            .subscribe(
                { response: CommonResponse? ->
                    resendOtpliveData.setValue(
                        Resource.success(response)
                    )
                },
                { throwable: Throwable? ->
                    resendOtpliveData.setValue(
                        Resource.error(throwable!!)
                    )
                }
            ))

    }

}

