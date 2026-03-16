/*
 * Copyright (C) 2018 The Android Open Source Project
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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.datwdt.ViewModelFactory
import com.app.datwdt.viewmodel.auth.SecurityCodeCheckViewModel
import com.app.datwdt.viewmodel.auth.LoginViewModel
import com.app.datwdt.viewmodel.main.*
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Since dagger
 * support multibinding you are free to bind as may ViewModels as you want.
 */
@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SecurityCodeCheckViewModel::class)
    abstract fun bindSecurityCodeCheckViewModel(viewModel: SecurityCodeCheckViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(WelcomeViewModel::class)
    abstract fun bindWelcomeViewModel(viewModel: WelcomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CreateNewGroupViewModel::class)
    abstract fun bindCreateNewGroupViewModel(viewModel: CreateNewGroupViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SuccessCreateGroupViewModel::class)
    abstract fun bindSuccessCreateGroupViewModel(viewModel: SuccessCreateGroupViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ExistingGroupViewModel::class)
    abstract fun bindExistingGroupViewModel(viewModel: ExistingGroupViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EditGroupViewModel::class)
    abstract fun bindEditGroupViewModel(viewModel: EditGroupViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NeedHelpViewModel::class)
    abstract fun bindNeedHelpViewModel(viewModel: NeedHelpViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UpdateMyDetailsViewModel::class)
    abstract fun bindUpdateMyDetailsViewModel(viewModel: UpdateMyDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MenuViewModel::class)
    abstract fun bindMenuViewModel(viewModel: MenuViewModel): ViewModel
}