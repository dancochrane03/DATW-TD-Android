package com.app.datwdt.viewmodel.main

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.datwdt.R
import com.app.datwdt.constants.Constants
import com.app.datwdt.data.main.Resource
import com.app.datwdt.data.main.RestClient
import com.app.datwdt.data.model.CommonResponse
import com.app.datwdt.data.model.main.AddFilesResposne
import com.app.datwdt.data.model.main.CreateGroupResponse
import com.app.datwdt.data.repository.MainRepository
import com.app.datwdt.databinding.*
import com.app.datwdt.util.FormValidation
import com.app.datwdt.util.Preferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.HashMap
import javax.inject.Inject


class SuccessCreateGroupViewModel @Inject
constructor(var mainRepository: MainRepository) : ViewModel() {

    lateinit var activity: Activity
    lateinit var mBinding: FragmentSuccessCreateGroupBinding

    @Inject
    lateinit var preferences: Preferences

    //API
    private val compositeDisposable = CompositeDisposable()


    var _groupname: MutableLiveData<String> = MutableLiveData()
    var _group_id: MutableLiveData<String> = MutableLiveData()
    var strImage = MutableLiveData<String>()

    init {
//        _emailAddress.value = "shaikhvahida33@gmail.com"
//        _password.value = "12345678"

    }

    fun initialize(activity: Activity, mBinding: FragmentSuccessCreateGroupBinding) {
        this.activity = activity
        this.mBinding = mBinding
        preferences=Preferences(activity)
    }



    private val addPhotosliveData = MutableLiveData<Resource<AddFilesResposne>>()

    fun addPhotosliveData(): MutableLiveData<Resource<AddFilesResposne>> {
        return addPhotosliveData
    }


    fun addPhotosApi() {
        val params = HashMap<String, RequestBody>()
        params.put(Constants.group_id, RestClient.createRequestBody(_group_id.value.toString()))
        params.put(Constants.entity_id, RestClient.createRequestBody(preferences.getString(Constants.entity_id)
            .toString()))

        var body: MultipartBody.Part? =null
        if (strImage.getValue() != null && !strImage.value?.isEmpty()!!) {
            val file: File = File(strImage.value)
            body = MultipartBody.Part.createFormData(
                Constants.file,
                file.name, RestClient.createRequestBody(file)
            )
        }

        compositeDisposable.add(mainRepository.addPhotosAPI(params,body)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { disposable: Disposable ->
                addPhotosliveData.setValue(
                    Resource.loading()
                )
            }
            .subscribe(
                { response: AddFilesResposne? ->
                    addPhotosliveData.setValue(
                        Resource.success(response)
                    )
                },
                { throwable: Throwable? ->
                    addPhotosliveData.setValue(
                        Resource.error(throwable!!)
                    )
                }
            ))

    }

}

