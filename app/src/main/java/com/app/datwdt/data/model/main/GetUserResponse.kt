package com.app.datwdt.data.model.main


import com.google.gson.annotations.SerializedName

data class GetUserResponse(
    @SerializedName("result")
    var result: Result?,
    @SerializedName("success")
    var success: Boolean?,
    @SerializedName("message")
    var message: String?
) {
    data class Result(
        @SerializedName("address")
        var address: String?,
        @SerializedName("country_code")
        var countryCode: String?,
        @SerializedName("dob")
        var dob: String?,
        @SerializedName("email")
        var email: String?,
        @SerializedName("entity_id")
        var entityId: String?,
        @SerializedName("log_in_time")
        var logInTime: String?,
        @SerializedName("mfa_enabled")
        var mfaEnabled: String?,
        @SerializedName("name")
        var name: String?,
        @SerializedName("notes")
        var notes: String?,
        @SerializedName("phone_number")
        var phoneNumber: String?,
        @SerializedName("qf_ff_number")
        var qfFfNumber: String?,
        @SerializedName("role")
        var role: String?,
        @SerializedName("user_enabled")
        var userEnabled: String?,
        @SerializedName("user_pass")
        var userPass: String?
    )
}