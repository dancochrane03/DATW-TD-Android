package com.app.datwdt.viewmodel.auth

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.datwdt.R
import com.app.datwdt.constants.Constants
import com.app.datwdt.data.main.Resource
import com.app.datwdt.data.model.auth.UserResponse
import com.app.datwdt.databinding.FragmentLoginBinding
import com.app.datwdt.util.FormValidation
import com.app.datwdt.data.repository.AuthRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.HashMap
import javax.inject.Inject


class LoginViewModel @Inject
constructor(var authRepository: AuthRepository) : ViewModel() {

    lateinit var activity: Activity
    lateinit var mBinding: FragmentLoginBinding


    //API
    private val compositeDisposable = CompositeDisposable()


    var _username: MutableLiveData<String> = MutableLiveData()
    var _password: MutableLiveData<String> = MutableLiveData()

    init {
        _username.value = ""
        _password.value = ""

    }

    fun initialize(activity: Activity, mBinding: FragmentLoginBinding) {
        this.activity = activity
        this.mBinding = mBinding
    }

    //LIVE DATA
    private val userliveData = MutableLiveData<Resource<UserResponse>>()

    fun getUserliveData(): MutableLiveData<Resource<UserResponse>> {
        return userliveData
    }
    /**
     * CHECK VALIDATION
     *
     * @return
     */
    private fun isValidInput(): Boolean {

        val validation = FormValidation(activity)
        return if (!validation.isEmpty(
                _username.value,
                activity.getResources().getString(R.string.username_empty)
            )
        ) {
            mBinding.edtUsername.requestFocus()
            false
        } else if (!validation.isValidPassword(
                mBinding.edtPassword.getText().toString().trim(),
                activity.getResources().getString(R.string.password_empty),
                activity.getResources().getString(R.string.password_invalid)
            )
        ) {
            mBinding.edtPassword.requestFocus()
            false
        } else {
            true
        }
    }

    fun callLoginApi() {

        if (isValidInput()) {
            val params = HashMap<String, String>()
            params[Constants.user_name] = _username.value.toString()
            params[Constants.password] = _password.value.toString()

            compositeDisposable.add(authRepository.loginAPI(params)
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


}

