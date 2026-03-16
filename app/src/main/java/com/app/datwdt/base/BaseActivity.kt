package com.app.datwdt.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.ImageView
import androidx.annotation.IdRes
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.app.datwdt.R
import com.app.datwdt.util.GpsTracker
import com.app.datwdt.util.ImageLoaderUtils
import com.app.datwdt.util.Preferences
import com.bumptech.glide.request.RequestOptions
import dagger.android.AndroidInjection
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity() {

    @Inject
    lateinit var preferences: Preferences
    protected abstract fun initViewBinding()
    private lateinit var mDialog: Dialog
    var gpsTracker1: GpsTracker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        preferences = Preferences(this)
        initViewBinding()
    }

    fun showProgressDialog() {

        if (!mDialog.isShowing) {
            mDialog.show()
        }
    }

    fun dismissProgressDialog() {
        if (mDialog.isShowing) {
            mDialog.dismiss()
        }
    }


    fun replaceFragment(
        fragment: Fragment,
        backStackName: Boolean = false,
        popStack: Boolean = false,
        aTAG: String = "",
        @IdRes containerViewId: Int = R.id.main_content
    ) {


        var transition = supportFragmentManager
            .beginTransaction()
        transition.setCustomAnimations(
            R.anim.slide_in_from_right,
            R.anim.slide_out_from_left,
            R.anim.slide_in_from_left,
            R.anim.slide_out_from_right
        )

        if (popStack)
            supportFragmentManager.popBackStack()
//            supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        if (backStackName)
            transition.addToBackStack(aTAG)

        transition.replace(containerViewId, fragment).commit()

    }

    fun replaceFragment2(
        fragment: Fragment,
        backStackName: Boolean = false,
        popStack: Boolean = false,
        aTAG: String = "",
        @IdRes containerViewId: Int = R.id.main_content
    ) {


        var transition = supportFragmentManager
            .beginTransaction()
        transition.setCustomAnimations(
            R.anim.slide_in_from_right,
            R.anim.slide_out_from_left,
            R.anim.slide_in_from_left,
            R.anim.slide_out_from_right
        )

        if (popStack)
            supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        if (backStackName)
            transition.addToBackStack(aTAG)

        transition.replace(containerViewId, fragment).commit()

    }

    fun addFragment(
        @NonNull fragment: Fragment,
        backStackName: Boolean = false,
        aTAG: String = "",
        @IdRes containerViewId: Int = R.id.main_content
    ) {

        val transition = supportFragmentManager.beginTransaction()
        if (backStackName)
            transition.addToBackStack(aTAG)
        transition.setCustomAnimations(
            R.anim.slide_in_from_right,
            R.anim.slide_out_from_left,
            R.anim.slide_in_from_left,
            R.anim.slide_out_from_right
        )

        transition.add(containerViewId, fragment).commit()
    }

    fun isFragmentAlreadyInStack(tag1: String): Boolean {
        if (supportFragmentManager.backStackEntryCount > 0) {
            val backEntry =
                supportFragmentManager.getBackStackEntryAt(supportFragmentManager.backStackEntryCount - 1)
            val tag = backEntry.name

            return (tag.equals(tag1))
        } else {
            return false
        }
    }

    companion object {
        /**
         * SET DATA TO VIEW
         */
        fun setDataToViewNoTransform(
            context: Context?,
            strImage: String?,
            imageView: ImageView,
            intPlaceholder: Int
        ) {
            if (strImage != null && strImage != "") {
                ImageLoaderUtils.loadImageFromUrlWithoutResizeViaGlide(
                    context,
                    strImage,
                    imageView,
                    intPlaceholder,
                    RequestOptions.noTransformation()
                )
            }
        }


        /**
         * SET DATA TO VIEW
         */
        fun setDataToView(
            context: Context?,
            strImage: String?,
            imageView: ImageView,
            intPlaceholder: Int
        ) {
            if (strImage != null && strImage != "") {
                ImageLoaderUtils.loadImageFromUrlWithoutResizeViaGlide(
                    context,
                    strImage,
                    imageView,
                    intPlaceholder,
                    RequestOptions.centerCropTransform()
                )
            }
        }

        fun getDate(date: String): String {
            if (date != null) {
                val fmt = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
                val outputFormat = SimpleDateFormat("hh:mm", Locale.US)
//            val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
                val date = fmt.parse(date)
                val formattedDate = outputFormat.format(date!!)

                return formattedDate.toString()
            } else {
                return ""
            }
        }

        fun getDateFull(date: String): String {
            if (date != null) {
                val fmt = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
                val outputFormat = SimpleDateFormat("MMMM d, yyyy hh:mm:ss a", Locale.US)
//            val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
                val date = fmt.parse(date)
                val formattedDate = outputFormat.format(date!!)

                return formattedDate.toString()
            } else {
                return ""
            }
        }
    }
}