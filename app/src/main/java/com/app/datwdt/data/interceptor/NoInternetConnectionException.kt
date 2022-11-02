package com.app.datwdt.data.interceptor

import android.content.Context
import com.app.datwdt.R
import java.io.IOException

class NoInternetConnectionException internal constructor(private val context: Context) :
    IOException() {
    override val message: String
        get() = context.resources.getString(R.string.internet_error)
}