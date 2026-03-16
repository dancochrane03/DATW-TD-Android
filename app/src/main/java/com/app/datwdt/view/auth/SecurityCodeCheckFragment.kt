package com.app.datwdt.view.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.datwdt.R
import com.app.datwdt.ViewModelFactory
import com.app.datwdt.base.BaseFragment
import com.app.datwdt.constants.Constants
import com.app.datwdt.data.main.Resource
import com.app.datwdt.data.model.CommonResponse
import com.app.datwdt.data.model.auth.UserResponse
import com.app.datwdt.databinding.FragmentSecurityCodeCheckBinding
import com.app.datwdt.view.main.MainActivity
import com.app.datwdt.viewmodel.auth.SecurityCodeCheckViewModel
import com.google.gson.Gson
import javax.inject.Inject


class SecurityCodeCheckFragment() : BaseFragment(), View.OnClickListener {

    private lateinit var mBinding: FragmentSecurityCodeCheckBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var securityCodeCheckViewModel: SecurityCodeCheckViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentSecurityCodeCheckBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun initViewModel() {
        securityCodeCheckViewModel = viewModelFactory.create(SecurityCodeCheckViewModel::class.java)
        mBinding.setLifecycleOwner(this)
        securityCodeCheckViewModel.initialize(requireActivity(), mBinding)
        mBinding.securityCodeCheckViewModel = securityCodeCheckViewModel
    }

    override fun observeViewModel() {
        securityCodeCheckViewModel.getUserLiveData()
            .observe(this) { response: Resource<UserResponse> ->
                consumeAPIResponse(
                    response
                )
            }

        securityCodeCheckViewModel.getResendOtpLiveData()
            .observe(this) { response: Resource<CommonResponse> ->
                consumeResendOtpAPIResponse(
                    response
                )
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.btnLogin.setOnClickListener(this)
        mBinding.btnDidntReceiveCode.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnLogin -> {
                securityCodeCheckViewModel.callVerifyOtpApi()
            }
            R.id.btnDidntReceiveCode -> {
                securityCodeCheckViewModel.callResendOtpApi()
            }
        }
    }

    private fun consumeAPIResponse(response: Resource<UserResponse>) {
        when (response.status) {
            Resource.Status.LOADING -> showProgress()
            Resource.Status.ERROR -> {
                hideProgress()
                if (response.error != null && response.error.message != null) showMessage(
                    response.error.message
                )
            }
            Resource.Status.SUCCESS -> {
                hideProgress()
                if (response.data != null) {
                    if (response.data.success!!) {
                        preferences.putString(Constants.entity_id,response.data.userInfo?.entityId)
                        preferences.putString(Constants.user_gson, Gson().toJson(response.data.userInfo))
                        preferences.putString(Constants.user_role, response.data.userInfo?.role)
                        showMessage(getString(R.string.text_login_success))
                        navigateToMain()


                    } else {
                        shoSwMessage(response.data.message)
                    }
                }
            }
        }
    }

    private fun consumeResendOtpAPIResponse(response: Resource<CommonResponse>) {
        when (response.status) {
            Resource.Status.LOADING -> showProgress()
            Resource.Status.ERROR -> {
                hideProgress()
                if (response.error != null && response.error.message != null) showMessage(
                    response.error.message
                )
            }
            Resource.Status.SUCCESS -> {
                hideProgress()
                if (response.data != null) {
                    if (response.data.success!!) {

                        showMessage(response.data.message)
                    } else {
                        shoSwMessage(response.data.message)
                    }
                }
            }
        }
    }


    private fun navigateToMain() {
        val destIntent = MainActivity.newIntent(baseActivity!!)
        startActivity(destIntent)
        baseActivity!!.finish()

    }


}