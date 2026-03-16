package com.app.datwdt.data.repository

import com.app.datwdt.data.model.CommonResponse
import com.app.datwdt.data.model.auth.UserResponse
import com.app.datwdt.data.main.AuthApi
import io.reactivex.Observable
import javax.inject.Inject


class AuthRepository @Inject internal constructor(private val apiService: AuthApi) {

    fun loginAPI(params: Map<String, String>): Observable<UserResponse> {
        return apiService.loginAPI(params)
    }

    fun verifyOtpAPI(params: Map<String, String>): Observable<UserResponse> {
        return apiService.verifyOtpAPI(params)
    }

    fun resendOtpAPI(params: Map<String, String>): Observable<CommonResponse> {
        return apiService.resendOtpAPI(params)
    }
}