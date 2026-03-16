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
import com.app.datwdt.databinding.FragmentUpdateMyDetailsBinding
import com.app.datwdt.viewmodel.main.UpdateMyDetailsViewModel
import com.google.gson.Gson
import javax.inject.Inject


class UpdateMyDetailsFragment() : BaseFragment(), View.OnClickListener {

    private lateinit var mBinding: FragmentUpdateMyDetailsBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var updateMyDetailsViewModel: UpdateMyDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentUpdateMyDetailsBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun initViewModel() {
        updateMyDetailsViewModel = viewModelFactory.create(UpdateMyDetailsViewModel::class.java)
        mBinding.setLifecycleOwner(this)
        updateMyDetailsViewModel.initialize(requireActivity(), mBinding)
        mBinding.updateMyDetailsViewModel = updateMyDetailsViewModel
        updateMyDetailsViewModel.callGetUserApi()
    }

    override fun observeViewModel() {
        updateMyDetailsViewModel.getUserliveData()
            .observe(this) { response: Resource<GetUserResponse> ->
                consumeAPIResponse(
                    response
                )
            }
        updateMyDetailsViewModel.getUpdateUserliveData()
            .observe(this) { response: Resource<GetUserResponse> ->
                consumeAPIResponseUpdate(
                    response
                )
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.header.tvTitleMenu.setText(getString(R.string.text_update_my_details))
        mBinding.btnSave.setOnClickListener(this)
        mBinding.header.btnMenu.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnSave -> {
                updateMyDetailsViewModel.updateUserDetailsApi()
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
                        updateMyDetailsViewModel._emailAddress.value = response.data.result?.email
                        updateMyDetailsViewModel._password.value = response.data.result?.userPass
                        updateMyDetailsViewModel._address.value = response.data.result?.address
                        updateMyDetailsViewModel._phoneNumber.value =
                            response.data.result?.phoneNumber
                        updateMyDetailsViewModel._dob.value = response.data.result?.dob
                        updateMyDetailsViewModel._ffnumber.value = response.data.result?.qfFfNumber

                    } else {
                        shoSwMessage(response.data.message)
                    }
                }
            }
        }
    }

    private fun consumeAPIResponseUpdate(response: Resource<GetUserResponse>) {
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
                        updateMyDetailsViewModel._emailAddress.value = response.data.result?.email
                        updateMyDetailsViewModel._password.value = response.data.result?.userPass
                        updateMyDetailsViewModel._address.value = response.data.result?.address
                        updateMyDetailsViewModel._phoneNumber.value =
                            response.data.result?.phoneNumber
                        updateMyDetailsViewModel._dob.value = response.data.result?.dob
                        updateMyDetailsViewModel._ffnumber.value = response.data.result?.qfFfNumber
                        shoSwMessage(resources.getString(R.string.profile_update_success))

                    } else {
                        shoSwMessage(response.data.message)
                    }
                }
            }
        }
    }


}