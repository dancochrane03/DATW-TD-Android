package com.app.datwdt.base


import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.annotation.NonNull
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.app.datwdt.R
import com.app.datwdt.constants.Constants
import com.app.datwdt.util.*
import com.app.datwdt.view.auth.AuthActivity
import com.app.datwdt.view.main.*
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


abstract class BaseFragment : Fragment(), MenuFragment.OnMenuClickListener {

    @Inject
    lateinit var preferences: Preferences
    protected abstract fun initViewModel()
    protected abstract fun observeViewModel()

    var baseActivity: BaseActivity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
        baseActivity = activity as BaseActivity
    }


    fun showProgressDialog() {
        baseActivity?.showProgressDialog()
    }

    fun dismissProgressDialog() {
        baseActivity?.dismissProgressDialog()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        observeViewModel()
    }

    fun replaceFragment(
        @NonNull fragment: Fragment,
        backStackName: Boolean = false,
        popStack: Boolean = false,
        aTAG: String = "",
        @IdRes containerViewId: Int = R.id.main_content
    ) {
        baseActivity!!.replaceFragment(fragment, backStackName, popStack, aTAG, containerViewId)
    }

    fun addFragment(
        @NonNull fragment: Fragment,
        backStackName: Boolean = false,
        aTAG: String = "",
        @IdRes containerViewId: Int = R.id.main_content

    ) {
        baseActivity!!.addFragment(fragment, backStackName, aTAG, containerViewId)
    }

    fun isFragmentAlreadyInStack(tag1: String): Boolean {
        if (baseActivity!!.supportFragmentManager.backStackEntryCount > 0) {
            val backEntry =
                baseActivity!!.supportFragmentManager.getBackStackEntryAt(baseActivity!!.supportFragmentManager.backStackEntryCount - 1)
            val tag = backEntry.name

            return (tag.equals(tag1))
        } else {
            return false
        }
    }

    fun makeLinks(textView: TextView, vararg links: Pair<String, View.OnClickListener>) {
        var textView: TextView = textView
        if (!textView.text.isEmpty()) {

            val spannableString = SpannableString(textView.text)
            for (link in links) {
                val clickableSpan = object : ClickableSpan() {
                    override fun onClick(view: View) {
                        Selection.setSelection((view as TextView).text as Spannable, 0)
                        view.invalidate()
                        link.second.onClick(view)
                    }

                    override fun updateDrawState(ds: TextPaint) {
                        ds.isUnderlineText = false
                    }
                }
                val startIndexOfLink = textView.text.toString().indexOf(link.first)
                spannableString.setSpan(
                    clickableSpan, startIndexOfLink, startIndexOfLink + link.first.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )

                val myTypeface = Typeface.create(
                    ResourcesCompat.getFont(
                        requireContext(),
                        R.font.montserrat_bold
                    ), Typeface.NORMAL
                )

                spannableString.setSpan(
                    CustomTypefaceSpan("", myTypeface),
                    startIndexOfLink,
                    startIndexOfLink + link.first.length,
                    Spanned.SPAN_EXCLUSIVE_INCLUSIVE
                )


            }



            textView.movementMethod =
                LinkMovementMethod.getInstance() // without LinkMovementMethod, link can not click
            textView.setText(spannableString, TextView.BufferType.SPANNABLE)
        }
    }


    open fun showProgress() {
        ProgressDialog.instance?.show(requireContext())
    }

    open fun hideProgress() {
        ProgressDialog.instance?.dismiss()
    }

    protected open fun showMessage(message: String?) {
        Utils.makeToast(requireContext(), message)
    }

    protected open fun shoSwMessage(message: String?) {
        Utils.makeToast(requireContext(), message)
//        Utils.showSnackBar(message, requireActivity())
    }

    fun getValueFromBundle(bundle: Bundle?, keyName: String): String {
        if (bundle != null) {
            if (bundle.containsKey(keyName)) {
                return bundle.getString(keyName).toString()
            } else {
                return ""
            }
        } else {
            return ""
        }
    }

    fun popFragmentTillLogin() {

        for (i in (baseActivity?.supportFragmentManager?.backStackEntryCount!! - 1) downTo 0) {
            // Get all Fragment list.
            val count = i

            val backEntry: FragmentManager.BackStackEntry =
                baseActivity?.supportFragmentManager!!.getBackStackEntryAt(
                    count
                )
            val tag: String = backEntry.name.toString()

            val fragment =
                baseActivity?.supportFragmentManager?.findFragmentByTag(
                    tag
                )

            if (tag.equals("LoginFragment")) {
                return
            } else {
                baseActivity?.supportFragmentManager?.popBackStack()
            }
        }

    }

    override fun setOnMenuClick(click: Int) {
        when (click) {
            0 -> {
                preferences.putString(Constants.LAST_LOADED_FRAGMENT, "CreateNewGroupFragment")
                replaceFragment(CreateNewGroupFragment(), false, true, "CreateNewGroupFragment")
            }
            1 -> {
                preferences.putString(Constants.LAST_LOADED_FRAGMENT, "ExistingGroupFragment")
                replaceFragment(ExistingGroupFragment(), false, true, "ExistingGroupFragment")
            }
            2 -> {
                preferences.putString(Constants.LAST_LOADED_FRAGMENT, "UpdateMyDetailsFragment")
                replaceFragment(UpdateMyDetailsFragment(), false, true, "UpdateMyDetailsFragment")
            }
            3 -> {
                preferences.putString(Constants.LAST_LOADED_FRAGMENT, "NeedHelpFragment")
                replaceFragment(NeedHelpFragment(), false, true, "NeedHelpFragment")
            }
            4 -> {
                GlobalMethods.setDialog(
                    requireContext(),
                    context?.resources?.getString(R.string.text_are_you_sure_you_want_to_logout),
                    object : GlobalMethods.DialogListener2 {
                        override fun setOnYesClick() {
                            preferences.clear()
                            navigateToAuth()
                        }

                        override fun setOnNoClick() {

                        }

                    }
                )
            }

        }
    }

    private fun navigateToAuth() {
        val destIntent = AuthActivity.newIntent(requireContext())
        activity?.startActivity(destIntent)
        activity?.finish()
    }

}

