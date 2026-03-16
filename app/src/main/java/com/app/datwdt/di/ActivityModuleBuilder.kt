/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.app.datwdt.di
import com.app.datwdt.SplashActivity
import com.app.datwdt.view.auth.AuthActivity
import com.app.datwdt.view.auth.LoginFragment
import com.app.datwdt.view.auth.SecurityCodeCheckFragment
import com.app.datwdt.view.main.*
import com.app.datwdt.viewmodel.main.UpdateMyDetailsViewModel
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class ActivityModuleBuilder {

    @ContributesAndroidInjector()
    abstract fun contributeSplashActivity(): SplashActivity

    @ContributesAndroidInjector()
    abstract fun contributeAuthActivity(): AuthActivity


    @ContributesAndroidInjector()
    abstract fun contributeMainActivity(): MainActivity


    @ContributesAndroidInjector()
    abstract fun contributeLoginFragment(): LoginFragment

    @ContributesAndroidInjector()
    abstract fun contributeSecurityCodeCheckFragment(): SecurityCodeCheckFragment


    @ContributesAndroidInjector()
    abstract fun contributeWelcomeFragment(): WelcomeFragment

    @ContributesAndroidInjector()
    abstract fun contributeCreateNewGroupFragment(): CreateNewGroupFragment

    @ContributesAndroidInjector()
    abstract fun contributeSuccessCreateGroupFragment(): SuccessCreateGroupFragment

    @ContributesAndroidInjector()
    abstract fun contributeExistingGroupFragment(): ExistingGroupFragment

    @ContributesAndroidInjector()
    abstract fun contributeEditGroupFragment(): EditGroupFragment

    @ContributesAndroidInjector()
    abstract fun contributeNeedHelpFragment(): NeedHelpFragment

    @ContributesAndroidInjector()
    abstract fun contributeUpdateMyDetailsFragment(): UpdateMyDetailsFragment

    @ContributesAndroidInjector()
    abstract fun contributeMenuFragment(): MenuFragment

}