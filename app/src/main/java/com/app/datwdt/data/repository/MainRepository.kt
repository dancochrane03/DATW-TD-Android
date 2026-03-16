package com.app.datwdt.data.repository

import com.app.datwdt.data.model.auth.UserResponse
import com.app.datwdt.data.main.MainApi
import com.app.datwdt.data.model.CommonResponse
import com.app.datwdt.data.model.FilesListResponse
import com.app.datwdt.data.model.main.AddFilesResposne
import com.app.datwdt.data.model.main.CreateGroupResponse
import com.app.datwdt.data.model.main.GetUserResponse
import com.app.datwdt.data.model.main.GroupListResponse
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class MainRepository @Inject internal constructor(private val apiService: MainApi) {

    fun getUserAPI(params: Map<String, String>): Observable<GetUserResponse> {
        return apiService.getUserApi(params)
    }

    fun updateUserAPI(params: Map<String, String>): Observable<GetUserResponse> {
        return apiService.updateUserApi(params)
    }

    fun createGroupAPI(params: Map<String, String>): Observable<CreateGroupResponse> {
        return apiService.createGroupApi(params)
    }

    fun addPhotosAPI(params: Map<String, RequestBody>, body: MultipartBody.Part?): Observable<AddFilesResposne> {
        return apiService.addPhotosAPI(params,body)
    }

    fun getGroupListAPI(params: Map<String, String>): Observable<GroupListResponse> {
        return apiService.getGroupListApi(params)
    }

    fun getFilesListApi(params: Map<String, String>): Observable<FilesListResponse> {
        return apiService.getFilesListApi(params)
    }

    fun notifyApi(params: Map<String, String>): Observable<CommonResponse> {
        return apiService.notifyApi(params)
    }

    fun deleteFileApi(params: Map<String, String>): Observable<CommonResponse> {
        return apiService.deleteFileApi(params)
    }
}