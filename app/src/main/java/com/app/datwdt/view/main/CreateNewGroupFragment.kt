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
import com.app.datwdt.data.model.main.CreateGroupResponse
import com.app.datwdt.data.model.main.GetUserResponse
import com.app.datwdt.databinding.FragmentCreateNewGroupBinding
import com.app.datwdt.databinding.FragmentSecurityCodeCheckBinding
import com.app.datwdt.databinding.FragmentWelcomeBinding
import com.app.datwdt.view.auth.SecurityCodeCheckFragment
import com.app.datwdt.viewmodel.auth.SecurityCodeCheckViewModel
import com.app.datwdt.viewmodel.auth.LoginViewModel
import com.app.datwdt.viewmodel.main.CreateNewGroupViewModel
import com.app.datwdt.viewmodel.main.WelcomeViewModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_menu.view.*
import kotlinx.android.synthetic.main.layout_header.view.*
import javax.inject.Inject


class CreateNewGroupFragment() : BaseFragment(), View.OnClickListener {

    private lateinit var mBinding: FragmentCreateNewGroupBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var createNewGroupViewModel: CreateNewGroupViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentCreateNewGroupBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun initViewModel() {
        createNewGroupViewModel = viewModelFactory.create(CreateNewGroupViewModel::class.java)
        mBinding.setLifecycleOwner(this)
        createNewGroupViewModel.initialize(requireActivity(), mBinding)
        mBinding.createNewGroupViewModel = createNewGroupViewModel
    }

    override fun observeViewModel() {
        createNewGroupViewModel.createGroupliveData()
            .observe(this) { response: Resource<CreateGroupResponse> ->
                consumeAPIResponse(
                    response
                )
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.header.tvTitleMenu.setText(getString(R.string.text_create_new_group))
        mBinding.btnCreateGroup.setOnClickListener(this)
        mBinding.llHeader.header.btnMenu.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnCreateGroup -> {
                createNewGroupViewModel.createGroupApi()
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

    private fun consumeAPIResponse(response: Resource<CreateGroupResponse>) {
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
                        var fragment = SuccessCreateGroupFragment()
                        var bundle = Bundle()
                        bundle.putString(
                            Constants.group_nm,
                            createNewGroupViewModel._groupname.value.toString()
                        )
                        bundle.putString(
                            Constants.group_id,
                            response.data.result?.id
                        )
                        fragment.arguments = bundle

                        replaceFragment(
                            fragment,
                            false,
                            false,
                            "SuccessCreateGroupFragment"
                        )

                    } else {
                        showMessage(response.data.message)
                    }
                }
            }
        }
    }


}