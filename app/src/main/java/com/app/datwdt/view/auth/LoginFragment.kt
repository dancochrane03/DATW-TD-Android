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
import com.app.datwdt.data.model.auth.UserResponse
import com.app.datwdt.databinding.FragmentLoginBinding
import com.app.datwdt.view.main.MainActivity
import com.app.datwdt.viewmodel.auth.LoginViewModel
import javax.inject.Inject


class LoginFragment: BaseFragment(), View.OnClickListener {

    private lateinit var mBinding: FragmentLoginBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentLoginBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun initViewModel() {
        loginViewModel = viewModelFactory.create(LoginViewModel::class.java)
        mBinding.setLifecycleOwner(this)
        loginViewModel.initialize(requireActivity(), mBinding)
        mBinding.loginViewModel = loginViewModel
    }

    override fun observeViewModel() {

        loginViewModel.getUserliveData().observe(this) { response: Resource<UserResponse> ->
            consumeAPIResponse(
                response
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.btnLogin.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnLogin -> {
                loginViewModel.callLoginApi()
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
                        if (response.data.userInfo != null) {
                            if(response.data.userInfo!!.role.equals("admin")){
                                shoSwMessage(resources.getString(R.string.admin_login_error))
                                return
                            }
                            preferences.storeUserDetails(response.data)
                            preferences.putString(Constants.user_role, response.data.userInfo?.role)
                            showMessage(getString(R.string.text_login_success))
                            navigateToMain()
                        } else {
                            preferences.putString(Constants.access_token, response.data.accessToken)
                            showMessage(response.data.message)
                            replaceFragment(
                                SecurityCodeCheckFragment(),
                                true,
                                false,
                                "SecurityCodeCheckFragment"
                            )
                        }
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