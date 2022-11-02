package com.app.datwdt.view.main

import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.app.datwdt.R
import com.app.datwdt.ViewModelFactory
import com.app.datwdt.base.BaseFragment
import com.app.datwdt.databinding.*
import com.app.datwdt.view.auth.SecurityCodeCheckFragment
import com.app.datwdt.viewmodel.auth.SecurityCodeCheckViewModel
import com.app.datwdt.viewmodel.auth.LoginViewModel
import com.app.datwdt.viewmodel.main.CreateNewGroupViewModel
import com.app.datwdt.viewmodel.main.MenuViewModel
import com.app.datwdt.viewmodel.main.SuccessCreateGroupViewModel
import com.app.datwdt.viewmodel.main.WelcomeViewModel
import javax.inject.Inject


class MenuFragment() : DialogFragment(), View.OnClickListener {

    private lateinit var mBinding: FragmentMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.MaterialDialogSheet)

    }

    override fun setupDialog(dialog: Dialog, style: Int) {
        //  super.setupDialog(dialog, style)
        dialog.window!!.setBackgroundDrawableResource(R.color.transparent)
        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        dialog.window!!.setGravity(Gravity.CENTER)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentMenuBinding.inflate(layoutInflater)
        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.header.tvTitleMenu.setText(getString(R.string.text_menu))
        mBinding.btnCreateNewGroup.setOnClickListener(this)
        mBinding.btnEditViewAddGroup.setOnClickListener(this)
        mBinding.btnUpdateMyDetails.setOnClickListener(this)
        mBinding.btnHelp.setOnClickListener(this)
        mBinding.btnSignOut.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnCreateNewGroup -> {
                dialog!!.dismiss()
                listener.setOnMenuClick(0)
            }
            R.id.btnEditViewAddGroup -> {
                dialog!!.dismiss()
                listener.setOnMenuClick(1)
            }
            R.id.btnUpdateMyDetails -> {
                dialog!!.dismiss()
                listener.setOnMenuClick(2)
            }
            R.id.btnHelp -> {
                dialog!!.dismiss()
                listener.setOnMenuClick(3)
            }
            R.id.btnSignOut -> {
                listener.setOnMenuClick(4)
                dialog!!.dismiss()

            }
        }
    }

    lateinit var listener: OnMenuClickListener
    public fun setOnMenuClickListenerListener(listener: OnMenuClickListener) {
        this.listener = listener
    }

    interface OnMenuClickListener {
        fun setOnMenuClick(click: Int)
    }
}