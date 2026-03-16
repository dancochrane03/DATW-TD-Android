package com.app.datwdt.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.datwdt.R
import com.app.datwdt.ViewModelFactory
import com.app.datwdt.base.BaseFragment
import com.app.datwdt.databinding.*
import com.app.datwdt.view.auth.SecurityCodeCheckFragment
import com.app.datwdt.viewmodel.auth.SecurityCodeCheckViewModel
import com.app.datwdt.viewmodel.auth.LoginViewModel
import com.app.datwdt.viewmodel.main.CreateNewGroupViewModel
import com.app.datwdt.viewmodel.main.NeedHelpViewModel
import com.app.datwdt.viewmodel.main.SuccessCreateGroupViewModel
import com.app.datwdt.viewmodel.main.WelcomeViewModel
import javax.inject.Inject


class NeedHelpFragment() : BaseFragment(), View.OnClickListener {

    private lateinit var mBinding: FragmentNeedHelpBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var needHelpViewModel: NeedHelpViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentNeedHelpBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun initViewModel() {
        needHelpViewModel = viewModelFactory.create(NeedHelpViewModel::class.java)
        mBinding.setLifecycleOwner(this)
        needHelpViewModel.initialize(requireActivity(),mBinding)
        mBinding.needHelpViewModel = needHelpViewModel
    }

    override fun observeViewModel() {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.header.tvTitleMenu.setText(getString(R.string.text_need_help))
        mBinding.tvCantRememberLoginDetails.setOnClickListener(this)
        mBinding.header.btnMenu.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tvCantRememberLoginDetails -> {
                replaceFragment(UpdateMyDetailsFragment(), true, false, "UpdateMyDetailsFragment")

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


}