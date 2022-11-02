package com.app.datwdt.data.model.main


import com.google.gson.annotations.SerializedName

data class AddFilesResposne(
    @SerializedName("message")
    var message: String?,
    @SerializedName("result")
    var result: Result?,
    @SerializedName("success")
    var success: Boolean?
) {
    data class Result(
        @SerializedName("file_path")
        var filePath: String?,
        @SerializedName("file_thumb_path")
        var fileThumbPath: String?,
        @SerializedName("group_id")
        var groupId: String?
    )
}