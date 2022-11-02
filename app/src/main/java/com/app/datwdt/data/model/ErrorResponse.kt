package com.app.datwdt.data.model


import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("message")
    var message: String?,
    @SerializedName("success")
    var success: Boolean?
)