package com.app.datwdt.viewmodel.main

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.datwdt.R
import com.app.datwdt.constants.Constants
import com.app.datwdt.data.main.Resource
import com.app.datwdt.data.model.CommonResponse
import com.app.datwdt.data.model.main.GetUserResponse
import com.app.datwdt.data.model.main.GroupListResponse
import com.app.datwdt.data.repository.MainRepository
import com.app.datwdt.databinding.*
import com.app.datwdt.util.FormValidation
import com.app.datwdt.util.Preferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.HashMap
import javax.inject.Inject


class ExistingGroupViewModel @Inject
constructor(var mainRepository: MainRepository) : ViewModel() {

    lateinit var activity: Activity
    lateinit var mBinding: FragmentExistingGroupBinding

    @Inject
    lateinit var preferences: Preferences
    //API
    private val compositeDisposable = CompositeDisposable()


    //LIVE DATA
    private val groupListliveData = MutableLiveData<Resource<GroupListResponse>>()

    fun getGroupListliveData(): MutableLiveData<Resource<GroupListResponse>> {
        return groupListliveData
    }

    private val notifyliveData = MutableLiveData<Resource<CommonResponse>>()

    fun notifyliveData(): MutableLiveData<Resource<CommonResponse>> {
        return notifyliveData
    }

    fun initialize(activity: Activity, mBinding: FragmentExistingGroupBinding) {
        this.activity = activity
        this.mBinding = mBinding
        preferences=Preferences(activity)
    }

    fun callGetGroupListApi() {
        val params = HashMap<String, String>()

        if(preferences.getString(Constants.user_role).equals("admin",true)){
            params[Constants.entity_id] = preferences.getString(Constants.entity_id).toString()
        }

        compositeDisposable.add(mainRepository.getGroupListAPI(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { disposable: Disposable ->
                groupListliveData.setValue(
                    Resource.loading()
                )
            }
            .subscribe(
                { response: GroupListResponse? ->
                    groupListliveData.setValue(
                        Resource.success(response)
                    )
                },
                { throwable: Throwable? ->
                    groupListliveData.setValue(
                        Resource.error(throwable!!)
                    )
                }
            ))
    }

    fun notifyApi() {
        val params = HashMap<String, String>()

        params[Constants.group_name] = preferences.getString(Constants.entity_id).toString()


        compositeDisposable.add(mainRepository.deleteFileApi(params)
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

