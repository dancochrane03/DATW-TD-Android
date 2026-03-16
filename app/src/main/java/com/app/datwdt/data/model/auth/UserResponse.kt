package com.app.datwdt.data.model.auth


import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("accessToken")
    var accessToken: String?,
    @SerializedName("success")
    var success: Boolean?,
    @SerializedName("userInfo")
    var userInfo: UserInfo?,
    @SerializedName("message")
    var message: String?
) {
    data class UserInfo(
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
        @SerializedName("mfa_enabled")
        var mfaEnabled: String?,
        @SerializedName("name")
        var name: String?,
        @SerializedName("phone_number")
        var phoneNumber: String?,
        @SerializedName("profile_img")
        var profileImg: String?,
        @SerializedName("qf_ff_number")
        var qfFfNumber: String?,
        @SerializedName("role")
        var role: String?,
        @SerializedName("user_enabled")
        var userEnabled: String?
    )
}