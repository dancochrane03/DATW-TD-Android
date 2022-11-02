package com.app.datwdt.data.main

import com.app.datwdt.data.model.CommonResponse
import com.app.datwdt.data.model.auth.UserResponse
import io.reactivex.Observable
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApi {


    @FormUrlEncoded
    @POST("login.php")
    fun loginAPI(@FieldMap user: Map<String, String>): Observable<UserResponse>


    @FormUrlEncoded
    @POST("verify-otp.php")
    fun verifyOtpAPI(@FieldMap user: Map<String, String>): Observable<UserResponse>

    @FormUrlEncoded
    @POST("resend-otp.php")
    fun resendOtpAPI(@FieldMap user: Map<String, String>): Observable<CommonResponse>
}