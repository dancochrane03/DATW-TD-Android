package com.app.datwdt.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.datwdt.R
import com.app.datwdt.ViewModelFactory
import com.app.datwdt.base.BaseFragment
import com.app.datwdt.constants.Constants
import com.app.datwdt.data.main.Resource
import com.app.datwdt.data.model.main.GetUserResponse
import com.app.datwdt.databinding.FragmentSecurityCodeCheckBinding
import com.app.datwdt.databinding.FragmentWelcomeBinding
import com.app.datwdt.view.auth.SecurityCodeCheckFragment
import com.app.datwdt.viewmodel.auth.SecurityCodeCheckViewModel
import com.app.datwdt.viewmodel.auth.LoginViewModel
import com.app.datwdt.viewmodel.main.WelcomeViewModel
import com.google.gson.Gson
import javax.inject.Inject


class WelcomeFragment() : BaseFragment(), View.OnClickListener {

    private lateinit var mBinding: FragmentWelcomeBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var welcomeViewModel: WelcomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentWelcomeBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun initViewModel() {
        welcomeViewModel = viewModelFactory.create(WelcomeViewModel::class.java)
        mBinding.setLifecycleOwner(this)
        welcomeViewModel.initialize(requireActivity(), mBinding)
        mBinding.welcomeViewModel = welcomeViewModel

        var title = getString(R.string.text_title_menu)
        title = title.replace("<Name>", preferences.getUserResponse()?.name.toString())
        mBinding.header.tvTitleMenu.setText(title)
//        welcomeViewModel.callGetUserApi()
    }

    override fun observeViewModel() {
        welcomeViewModel.getUserliveData()
            .observe(this) { response: Resource<GetUserResponse> ->
                consumeAPIResponse(
                    response
                )
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.ivNewGroup.setOnClickListener(this)
        mBinding.tvNewGroup.setOnClickListener(this)
        mBinding.ivViewEdit.setOnClickListener(this)
        mBinding.tvViewEdit.setOnClickListener(this)
        mBinding.ivEditMyDetails.setOnClickListener(this)
        mBinding.tvEditMyDetails.setOnClickListener(this)
        mBinding.header.btnMenu.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ivNewGroup -> {
//                preferences.putString(Constants.LAST_LOADED_FRAGMENT, "CreateNewGroupFragment")
                replaceFragment(CreateNewGroupFragment(), true, false, "CreateNewGroupFragment")
            }
            R.id.tvNewGroup -> {
//                preferences.putString(Constants.LAST_LOADED_FRAGMENT, "CreateNewGroupFragment")
                replaceFragment(CreateNewGroupFragment(), true, false, "CreateNewGroupFragment")
            }
            R.id.ivViewEdit -> {
//                preferences.putString(Constants.LAST_LOADED_FRAGMENT, "ExistingGroupFragment")
                replaceFragment(ExistingGroupFragment(), true, false, "ExistingGroupFragment")
            }
            R.id.tvViewEdit -> {
//                preferences.putString(Constants.LAST_LOADED_FRAGMENT, "ExistingGroupFragment")
                replaceFragment(ExistingGroupFragment(), true, false, "ExistingGroupFragment")
            }
            R.id.ivEditMyDetails -> {
//                preferences.putString(Constants.LAST_LOADED_FRAGMENT, "UpdateMyDetailsFragment")
                replaceFragment(UpdateMyDetailsFragment(), true, false, "ExistingGroupFragment")
            }
            R.id.tvEditMyDetails -> {
//                preferences.putString(Constants.LAST_LOADED_FRAGMENT, "UpdateMyDetailsFragment")
                replaceFragment(UpdateMyDetailsFragment(), true, false, "ExistingGroupFragment")
            }
            R.id.btnMenu -> {
                var menuDialog = MenuFragment()
                if (menuDialog.isAdded) {
                    return
                }
                menuDialog.show(baseActivity?.supportFragmentManager!!, "Dialog Fragment")
                menuDialog.setOnMenuClickListenerListener(this)

            }
        }
    }


    private fun consumeAPIResponse(response: Resource<GetUserResponse>) {
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
                        preferences.putString(
                            Constants.user_gson,
                            Gson().toJson(response.data.result)
                        )
                        var title = getString(R.string.text_title_menu)
                        title = title.replace("<Name>", response.data.result?.name.toString())
                        mBinding.header.tvTitleMenu.setText(title)
                    } else {
                        shoSwMessage(response.data.message)
                    }
                }
            }
        }
    }


}