package com.app.datwdt.data.main

import com.app.datwdt.data.model.CommonResponse
import com.app.datwdt.data.model.FilesListResponse
import com.app.datwdt.data.model.main.AddFilesResposne
import com.app.datwdt.data.model.main.CreateGroupResponse
import com.app.datwdt.data.model.main.GetUserResponse
import com.app.datwdt.data.model.main.GroupListResponse
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*


interface MainApi {


    @GET("user.php")
    fun getUserApi(@QueryMap user: Map<String, String>): Observable<GetUserResponse>

    @FormUrlEncoded
    @POST("user.php")
    fun updateUserApi(@FieldMap user: Map<String, String>): Observable<GetUserResponse>

    @FormUrlEncoded
    @POST("groups.php")
    fun createGroupApi(@FieldMap user: Map<String, String>): Observable<CreateGroupResponse>

    @Multipart
    @POST("upload-group-file.php")
    fun addPhotosAPI(@PartMap params :Map<String,@JvmSuppressWildcards RequestBody>,
                               @Part body: MultipartBody.Part?
    ): Observable<AddFilesResposne>

    @GET("groups.php")
    fun getGroupListApi(@QueryMap user: Map<String, String>): Observable<GroupListResponse>

    @GET("files.php")
    fun getFilesListApi(@QueryMap user: Map<String, String>): Observable<FilesListResponse>

    @DELETE("files.php")
    fun deleteFileApi(@QueryMap user: Map<String, String>): Observable<CommonResponse>

    @FormUrlEncoded
    @POST("notify-office.php")
    fun notifyApi(@FieldMap user: Map<String, String>): Observable<CommonResponse>
}