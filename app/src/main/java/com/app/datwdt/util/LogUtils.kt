package com.app.datwdt.util

import android.util.Log
import com.app.datwdt.BuildConfig

object LogUtils {
    fun Print(tag: String?, text: String) {
        if (BuildConfig.DEBUG) Log.e(tag, "==========$text")
    }
}