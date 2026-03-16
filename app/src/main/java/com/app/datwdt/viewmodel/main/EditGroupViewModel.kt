package com.app.datwdt.viewmodel.main

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.datwdt.R
import com.app.datwdt.constants.Constants
import com.app.datwdt.data.main.Resource
import com.app.datwdt.data.main.RestClient
import com.app.datwdt.data.model.CommonResponse
import com.app.datwdt.data.model.FilesListResponse
import com.app.datwdt.data.model.main.AddFilesResposne
import com.app.datwdt.data.model.main.GroupListResponse
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


class EditGroupViewModel @Inject
constructor(var mainRepository: MainRepository) : ViewModel() {

    lateinit var activity: Activity
    lateinit var mBinding: FragmentEditGroupBinding

    @Inject
    lateinit var preferences: Preferences
    //API
    private val compositeDisposable = CompositeDisposable()


    var _group_id: MutableLiveData<String> = MutableLiveData()
    var group_name: MutableLiveData<String> = MutableLiveData()
    var strImage = MutableLiveData<String>()

    //LIVE DATA
    private val filesListliveData = MutableLiveData<Resource<FilesListResponse>>()

    fun getFilesListliveData(): MutableLiveData<Resource<FilesListResponse>> {
        return filesListliveData
    }

    private val deleteFileliveData = MutableLiveData<Resource<CommonResponse>>()

    fun deleteFilesliveData(): MutableLiveData<Resource<CommonResponse>> {
        return deleteFileliveData
    }

    private val notifyliveData = MutableLiveData<Resource<CommonResponse>>()

    fun notifyliveData(): MutableLiveData<Resource<CommonResponse>> {
        return notifyliveData
    }


    fun initialize(activity: Activity, mBinding: FragmentEditGroupBinding) {
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


    fun callGetFilesListApi() {
        val params = HashMap<String, String>()

        params[Constants.entity_id] = preferences.getString(Constants.entity_id).toString()
        params[Constants.group_id] = _group_id.value.toString()

        compositeDisposable.add(mainRepository.getFilesListApi(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { disposable: Disposable ->
                filesListliveData.setValue(
                    Resource.loading()
                )
            }
            .subscribe(
                { response: FilesListResponse? ->
                    filesListliveData.setValue(
                        Resource.success(response)
                    )
                },
                { throwable: Throwable? ->
                    filesListliveData.setValue(
                        Resource.error(throwable!!)
                    )
                }
            ))
    }

    fun deleteFileApi(file_id:String) {
        val params = HashMap<String, String>()

        params[Constants.entity_id] = preferences.getString(Constants.entity_id).toString()
        params[Constants.group_id] = _group_id.value.toString()
        params[Constants.file_id] = file_id

        compositeDisposable.add(mainRepository.deleteFileApi(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { disposable: Disposable ->
                deleteFileliveData.setValue(
                    Resource.loading()
                )
            }
            .subscribe(
                { response: CommonResponse? ->
                    deleteFileliveData.setValue(
                        Resource.success(response)
                    )
                },
                { throwable: Throwable? ->
                    deleteFileliveData.setValue(
                        Resource.error(throwable!!)
                    )
                }
            ))
    }


    fun notifyApi() {
        val params = HashMap<String, String>()

        params[Constants.group_name] = preferences.getString(Constants.entity_id).toString()


        compositeDisposable.add(mainRepository.notifyApi(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { disposable: Disposable ->
                notifyliveData.setValue(
                    Resource.loading()
                )
            }
            .subscribe(
                { response: CommonResponse? ->
                    notifyliveData.setValue(
                        Resource.success(response)
                    )
                },
                { throwable: Throwable? ->
                    notifyliveData.setValue(
                        Resource.error(throwable!!)
                    )
                }
            ))
    }
}

