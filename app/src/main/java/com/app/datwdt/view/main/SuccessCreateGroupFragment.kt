package com.app.datwdt.view.main

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.app.datwdt.R
import com.app.datwdt.ViewModelFactory
import com.app.datwdt.base.BaseFragment
import com.app.datwdt.constants.Constants
import com.app.datwdt.data.main.Resource
import com.app.datwdt.data.model.main.AddFilesResposne
import com.app.datwdt.databinding.FragmentSuccessCreateGroupBinding
import com.app.datwdt.viewmodel.main.SuccessCreateGroupViewModel
import com.jaiselrahman.filepicker.activity.FilePickerActivity
import com.jaiselrahman.filepicker.config.Configurations
import com.jaiselrahman.filepicker.model.MediaFile
import javax.inject.Inject


class SuccessCreateGroupFragment : BaseFragment(), View.OnClickListener {

    private lateinit var mBinding: FragmentSuccessCreateGroupBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var successCreateGroupViewModel: SuccessCreateGroupViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentSuccessCreateGroupBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun initViewModel() {
        successCreateGroupViewModel =
            viewModelFactory.create(SuccessCreateGroupViewModel::class.java)
        mBinding.lifecycleOwner = this
        successCreateGroupViewModel.initialize(requireActivity(), mBinding)
        mBinding.successCreateGroupViewModel = successCreateGroupViewModel
    }

    override fun observeViewModel() {
        successCreateGroupViewModel.addPhotosliveData()
            .observe(this) { response: Resource<AddFilesResposne> ->
                consumeAPIResponse(
                    response
                )
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getBundleData()
        mBinding.header.tvTitleMenu.text = getString(R.string.text_success)
        mBinding.btnAddPhotos.setOnClickListener(this)
    }

    private fun getBundleData() {
        var bundle = arguments

        if (bundle != null) {
            successCreateGroupViewModel._groupname.value =
                bundle.get(Constants.group_nm).toString()

            successCreateGroupViewModel._group_id.value =
                bundle.get(Constants.group_id).toString()


            var msg = getString(R.string.text_success_msg)
            msg = msg.replace("<Group Name>", successCreateGroupViewModel._groupname.value!!)

            mBinding.tvSuccessfully.text = msg

        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnAddPhotos -> {
                val intent = Intent(requireActivity(), FilePickerActivity::class.java)
                intent.putExtra(
                    FilePickerActivity.CONFIGS, Configurations.Builder()
                        .setCheckPermission(true)
                        .setShowImages(true)
                        .enableImageCapture(true)
                        .setSingleChoiceMode(true)
                        .setShowVideos(false)
                        .enableVideoCapture(false)
                        .setSkipZeroSizeFiles(true)
                        .build()
                )
                startActivityForResult(intent, Constants.FILE_REQUEST_CODE)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.FILE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null && data.getParcelableArrayListExtra<Parcelable>(FilePickerActivity.MEDIA_FILES) != null) {
                    val files: java.util.ArrayList<MediaFile> =
                        data.getParcelableArrayListExtra(FilePickerActivity.MEDIA_FILES)!!
                    if (files != null && files.size > 0) {
                        val mediaFile = files[0]
                        if (mediaFile.mediaType == MediaFile.TYPE_IMAGE) {
                            successCreateGroupViewModel.strImage.value = mediaFile.path
                        }

                        successCreateGroupViewModel.addPhotosApi()
                    }
                }
            }
        }
    }

    private fun consumeAPIResponse(response: Resource<AddFilesResposne>) {
        when (response.status) {
            Resource.Status.LOADING -> showProgress()
            Resource.Status.ERROR -> {
                hideProgress()
                if (response.error != null && response.error.message != null) showMessage(
                    response.error.message
                )
            }
            Resource.Status.SUCCESS -> {
                hideProgress()
                if (response.data != null) {
                    if (response.data.success!!) {
                        showMessage(response.data.message)
//                        activity?.onBackPressed()

                        var fragment = EditGroupFragment()
                        var bundle = Bundle()
                        bundle.putString(
                            Constants.group_id, response.data.result?.groupId
                        )
                        bundle.putString(
                            Constants.group_name, successCreateGroupViewModel._groupname.value
                        )
                        fragment.arguments = bundle

                        replaceFragment(fragment, true, false, "EditGroupFragment")
                    } else {
                        showMessage(response.data.message)
                    }
                }
            }
        }
    }

}