package com.app.datwdt.di.module

import com.app.datwdt.base.MyGlideModule
import com.bumptech.glide.module.AppGlideModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class GlideModule {
    @ContributesAndroidInjector
    abstract fun provideAppGlideModule(): MyGlideModule
}