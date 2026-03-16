package com.app.datwdt.data.model.main


import com.google.gson.annotations.SerializedName

data class GroupListResponse(
    @SerializedName("result")
    var result: List<Result?>?,
    @SerializedName("success")
    var success: Boolean?,
    @SerializedName("message")
    var message: String?
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