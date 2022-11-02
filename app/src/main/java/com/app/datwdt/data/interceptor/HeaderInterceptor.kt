package com.app.datwdt.data.interceptor

import android.content.Context
import android.os.Build
import com.app.datwdt.BuildConfig
import com.app.datwdt.constants.Constants
import com.app.datwdt.data.main.RestClient
import com.app.datwdt.util.Preferences
import com.app.datwdt.util.Utils
import io.reactivex.disposables.CompositeDisposable
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException


class HeaderInterceptor(var context: Context) : Interceptor {


    private lateinit var preferences: Preferences


    //API
    private val compositeDisposable = CompositeDisposable()


    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        preferences=Preferences(context)
        val original: Request = chain.request()

        if(!chain.request().url.toString().contains("login")){
            val builder: Request.Builder = original.newBuilder()
                .header("Content-Type", RestClient.CONTENT_TYPE)
                .header("Authorization", "Basic "+preferences.getString(Constants.access_token)!!)
                .method(original.method, original.body)

            val request: Request = builder.build()
            return chain.proceed(request)
        }else{
            val builder: Request.Builder = original.newBuilder()
                .header("Content-Type", RestClient.CONTENT_TYPE)
                .method(original.method, original.body)

            val request: Request = builder.build()
            return chain.proceed(request)
        }


    }

    private val TAG = "HeaderInterceptor"


}