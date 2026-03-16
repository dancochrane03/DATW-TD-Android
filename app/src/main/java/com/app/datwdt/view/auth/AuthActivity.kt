package com.app.datwdt.view.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.app.datwdt.base.BaseActivity
import com.app.datwdt.databinding.ActivityAuthBinding

class AuthActivity : BaseActivity() {

    public lateinit var binding: ActivityAuthBinding

    override fun initViewBinding() {
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        replaceFragment(LoginFragment(),false,true,"LoginFragment")

    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, AuthActivity::class.java)
        }
    }
}