package com.app.datwdt.view.main

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.GravityCompat
import com.app.datwdt.R
import com.app.datwdt.base.BaseActivity
import com.app.datwdt.constants.Constants
import com.app.datwdt.databinding.ActivityMainBinding
import com.app.datwdt.util.GlobalMethods
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

class MainActivity : BaseActivity(), GlobalMethods.DialogListener2 {

    public lateinit var binding: ActivityMainBinding

    override fun initViewBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferences.putString(Constants.LAST_LOADED_FRAGMENT, "WelcomeFragment")
        replaceFragment(WelcomeFragment(), false, true, "WelcomeFragment")

    }

    override fun onStart() {
        super.onStart()
        Dexter.withContext(this@MainActivity)
            .withPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    report?.let {
                        if (report.areAllPermissionsGranted()) {
                           /* Toast.makeText(
                                this@MainActivity,
                                "Permission granted successfully",
                                Toast.LENGTH_SHORT
                            ).show();*/
                        }else{
                            Toast.makeText(
                                this@MainActivity,
                                "Please grant permission from settings of app.",
                                Toast.LENGTH_SHORT
                            ).show();
                        }
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    // Remember to invoke this method when the custom rationale is closed
                    // or just by default if you don't want to use any custom rationale.
                    token?.continuePermissionRequest()
                }
            })
            .withErrorListener {
                Toast.makeText(
                    this@MainActivity,
                    "Permission denied.",
                    Toast.LENGTH_SHORT
                ).show();
            }
            .check()
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 0) {
            if (preferences.getString(Constants.LAST_LOADED_FRAGMENT).toString()
                    .equals("WelcomeFragment", true)
            ) {
                confirmExitApplication()
            } else {
                preferences.putString(Constants.LAST_LOADED_FRAGMENT, "WelcomeFragment")
                replaceFragment(WelcomeFragment(), false, true, "WelcomeFragment")
            }
        } else {
            super.onBackPressed()
        }
    }

    fun confirmExitApplication() {
        GlobalMethods.setDialog(
            this,
            resources.getString(R.string.text_exit_app),
            this
        )
    }

    override fun setOnYesClick() {
        super.onBackPressed()
    }

    override fun setOnNoClick() {
    }

}