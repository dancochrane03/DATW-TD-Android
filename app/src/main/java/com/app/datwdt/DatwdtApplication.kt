package com.app.datwdt

import android.content.Context
import com.app.datwdt.di.component.DaggerAppComponent
import com.app.datwdt.util.UnsafeOkHttpClient
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.InputStream
import javax.inject.Inject

class DatwdtApplication : DaggerApplication(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    override fun applicationInjector(): AndroidInjector<out DaggerApplication?> {
        return DaggerAppComponent.builder().application(this).build()
    }


    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        val okHttpClient = UnsafeOkHttpClient.unsafeOkHttpClient
        Glide.get(context).registry.replace(
            GlideUrl::class.java,
            InputStream::class.java, OkHttpUrlLoader.Factory(okHttpClient)
        )
//        FirebaseApp.initializeApp(applicationContext)


    }

    fun initDagger() {
        DaggerAppComponent.builder().build().inject(this)
    }

    companion object {
        lateinit var context: Context

        fun createRequestBody(s: String): RequestBody {
            return s
                .toRequestBody("multipart/form-data".toMediaTypeOrNull())
        }

        fun createRequestBody(file: File): RequestBody {
            return file
                .asRequestBody("multipart/form-data".toMediaTypeOrNull())
        }
    }
}