package com.app.datwdt.di.module

import com.app.datwdt.data.main.AuthApi
import com.app.datwdt.data.main.MainApi
import retrofit2.Retrofit
import dagger.Module
import dagger.Provides

@Module
internal object MainModule {

    @Provides
    fun provideAuthApi(retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    @Provides
    fun provideMainApi(retrofit: Retrofit): MainApi {
        return retrofit.create(MainApi::class.java)
    }
}