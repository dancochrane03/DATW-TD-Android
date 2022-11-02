package com.app.datwdt.data.model


import com.google.gson.annotations.SerializedName

data class FilesListResponse(
    @SerializedName("result")
    var result: List<Result?>?,
    @SerializedName("success")
    var success: Boolean?,
    @SerializedName("message")
    var message: String?
) {
    data class Result(
        @SerializedName("file_id")
        var fileId: String?,
        @SerializedName("file_path")
        var filePath: String?,
        @SerializedName("file_thumb_path")
        var fileThumbPath: String?,
        @SerializedName("group_id")
        var groupId: String?,
        @SerializedName("group_name")
        var groupName: String?,
        @SerializedName("image_path")
        var imagePath: String?,
        @SerializedName("submit_dt")
        var submitDt: String?,
        @SerializedName("user_name")
        var userName: String?
    )
}