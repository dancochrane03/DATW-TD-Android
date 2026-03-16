package com.app.datwdt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.app.datwdt.base.BaseActivity
import com.app.datwdt.constants.Constants
import com.app.datwdt.view.auth.AuthActivity
import com.app.datwdt.view.main.MainActivity

class SplashActivity : BaseActivity() {

    override fun initViewBinding() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        redirectToLoginAct()
    }

    private fun redirectToLoginAct() {
        Handler(Looper.getMainLooper()).postDelayed({

            if (preferences.getString(Constants.entity_id)!!.isEmpty()) {
                navigateToAuth()
            } else {
                navigateToMain()
            }
        }, 3000)
    }

    private fun navigateToAuth() {
        val destIntent = AuthActivity.newIntent(this@SplashActivity)
        startActivity(destIntent)
        finish()

    }

    private fun navigateToMain() {
        val destIntent = MainActivity.newIntent(this)
        startActivity(destIntent)
        finish()

    }
}