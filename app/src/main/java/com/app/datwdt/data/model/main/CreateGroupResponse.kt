package com.app.datwdt.data.model.main


import com.google.gson.annotations.SerializedName

data class CreateGroupResponse(
    @SerializedName("message")
    var message: String?,
    @SerializedName("result")
    var result: Result?,
    @SerializedName("success")
    var success: Boolean?
) {
    data class Result(
        @SerializedName("created_date")
        var createdDate: String?,
        @SerializedName("group_name")
        var groupName: String?,
        @SerializedName("id")
        var id: String?
    )
}