package com.app.datwdt.view.main

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
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
//        val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
//            listOf(
//                Manifest.permission.CAMERA,
//                Manifest.permission.READ_MEDIA_IMAGES,
//                Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED,
//            )
//        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
//            listOf(
//                Manifest.permission.CAMERA,
//                Manifest.permission.READ_MEDIA_IMAGES
//            )
//        }else{
//            listOf(
//                Manifest.permission.CAMERA,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                Manifest.permission.READ_EXTERNAL_STORAGE,
//            )
//        }
//
//        Dexter.withContext(this@MainActivity)
//            .withPermissions(permissions)
//            .withListener(object : MultiplePermissionsListener {
//
//                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
//                    report?.let {
//                        if (report.areAllPermissionsGranted()) {
//                            // ✅ All permissions granted → NOTHING DO
//                            return
//                        }
//                        // ❗ permanently denied → open settings
//                        if (report.isAnyPermissionPermanentlyDenied) {
//                            showSettingsDialog()
//                        } else if (!report.areAllPermissionsGranted()) {
//                            Toast.makeText(
//                                this@MainActivity,
//                                "Permission required to continue",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//                    }
//                }
//
//                override fun onPermissionRationaleShouldBeShown(
//                    permissions: MutableList<PermissionRequest>?,
//                    token: PermissionToken?
//                ) {
//                    token?.continuePermissionRequest()
//                }
//            })
//            .withErrorListener {
//                Toast.makeText(this, "Error occurred!", Toast.LENGTH_SHORT).show()
//            }
//            .check()
    }
    private fun showSettingsDialog() {
        AlertDialog.Builder(this)
            .setTitle("Permission Required")
            .setMessage("Please enable permission from Settings")
            .setPositiveButton("Go to Settings") { _, _ ->

                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivity(intent)
            }
            .setNegativeButton("Cancel", null)
            .show()
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