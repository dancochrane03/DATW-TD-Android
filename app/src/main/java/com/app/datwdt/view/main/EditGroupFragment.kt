package com.app.datwdt.view.main

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.datwdt.R
import com.app.datwdt.ViewModelFactory
import com.app.datwdt.base.BaseFragment
import com.app.datwdt.constants.Constants
import com.app.datwdt.data.main.Resource
import com.app.datwdt.data.model.CommonResponse
import com.app.datwdt.data.model.FilesListResponse
import com.app.datwdt.data.model.main.AddFilesResposne
import com.app.datwdt.databinding.*
import com.app.datwdt.implementor.RecyclerViewItemClickListener
import com.app.datwdt.util.DownloadTask
import com.app.datwdt.util.GlobalMethods
import com.app.datwdt.view.main.adapter.FilesAdapter
import com.app.datwdt.viewmodel.main.*
import com.jaiselrahman.filepicker.activity.FilePickerActivity
import com.jaiselrahman.filepicker.config.Configurations
import com.jaiselrahman.filepicker.model.MediaFile
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import javax.inject.Inject


class EditGroupFragment() : BaseFragment(), View.OnClickListener {

    private lateinit var mBinding: FragmentEditGroupBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var editGroupViewModel: EditGroupViewModel

    //adapter
    lateinit var editGrouptAdapter: FilesAdapter

    var filesList: ArrayList<FilesListResponse.Result> =
        ArrayList<FilesListResponse.Result>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentEditGroupBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun initViewModel() {

        editGroupViewModel = viewModelFactory.create(EditGroupViewModel::class.java)
        mBinding.setLifecycleOwner(this)
        editGroupViewModel.initialize(requireActivity(), mBinding)
        mBinding.editGroupViewModel = editGroupViewModel
        getBundleData()
        editGroupViewModel.callGetFilesListApi()
    }

    override fun observeViewModel() {
        editGroupViewModel.getFilesListliveData()
            .observe(this) { response: Resource<FilesListResponse> ->
                consumeAPIResponse(
                    response
                )
            }

        editGroupViewModel.addPhotosliveData()
            .observe(this) { response: Resource<AddFilesResposne> ->
                consumeAPIResponseAddPhotos(
                    response
                )
            }

        editGroupViewModel.deleteFilesliveData()
            .observe(this) { response: Resource<CommonResponse> ->
                consumeAPIResponseDeleteFiles(
                    response
                )
            }

        editGroupViewModel.notifyliveData()
            .observe(this) { response: Resource<CommonResponse> ->
                consumeAPIResponseNotify(
                    response
                )
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapter()
        mBinding.btnNotifyCompletion.setOnClickListener(this)
        mBinding.btnAddPhotos.setOnClickListener(this)
        mBinding.header.btnMenu.setOnClickListener(this)
    }

    private fun getBundleData() {
        var bundle = arguments

        if (bundle != null) {
            editGroupViewModel._group_id.value =
                bundle.get(Constants.group_id).toString()
            editGroupViewModel.group_name.value =
                bundle.get(Constants.group_name).toString()

        }
        mBinding.header.tvTitleMenu.setText(editGroupViewModel.group_name.value)
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnNotifyCompletion -> {
                editGroupViewModel.notifyApi()
//                replaceFragment(NeedHelpFragment(), true, false, "NeedHelpFragment")
            }
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
            R.id.btnMenu -> {
                var menuDialog = MenuFragment()
                if (menuDialog.isAdded) {
                    return
                }
                menuDialog.show(baseActivity?.supportFragmentManager!!, "Dialog Fragment")
                menuDialog.setOnMenuClickListenerListener(this)

            }
        }
    }

    private fun setAdapter() {

        //requests adapter
        filesList = ArrayList()

        mBinding.rvEditGroup.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        editGrouptAdapter =
            FilesAdapter(
                requireContext(),
                filesList,
                object : RecyclerViewItemClickListener {
                    override fun onItemClick(position: Int, flag: Int, view: View?) {
                        when(view?.id){
                            R.id.ivDelete->{
                                GlobalMethods.setDialog(
                                    requireContext(),
                                    context?.resources?.getString(R.string.text_are_you_sure_you_want_to_delete_file),
                                    object : GlobalMethods.DialogListener2 {
                                        override fun setOnYesClick() {
                                            editGroupViewModel.deleteFileApi(filesList.get(position).fileId.toString())
                                        }

                                        override fun setOnNoClick() {

                                        }

                                    }
                                )

                            }
                            R.id.ivLogo->{
                                download(position)

                            }
                        }

                    }
                })
        mBinding.rvEditGroup.setAdapter(editGrouptAdapter)

    }

    private fun download(position:Int) {
        Dexter.withContext(requireContext())
            .withPermissions(
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    report?.let {
                        if (report.areAllPermissionsGranted()) {
                            DownloadTask(requireContext(), filesList.get(position).filePath.toString())
                        }else{
                            Toast.makeText(
                                requireContext(),
                                "Please grant permission from settings of app.",
                                Toast.LENGTH_SHORT
                            ).show();
                        }
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    // Remember to invoke this method when the custom rationale is closed
                    // or just by default if you don't want to use any custom rationale.
                    token?.continuePermissionRequest()
                }
            })
            .withErrorListener {
                Toast.makeText(
                    requireContext(),
                    "Permission denied.",
                    Toast.LENGTH_SHORT
                ).show();
            }
            .check()
    }


    private fun consumeAPIResponse(response: Resource<FilesListResponse>) {
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
                        filesList.clear()
                        filesList.addAll(response.data.result as ArrayList<FilesListResponse.Result>)
                        editGrouptAdapter.notifyDataSetChanged()
                        if(filesList.size==0){
                            mBinding.tvDeleteMsg.visibility=View.GONE
                        }else{
                            mBinding.tvDeleteMsg.visibility=View.VISIBLE
                        }
                        showMessage(response.data.message)
                    } else {
                        showMessage(response.data.message)
                    }
                }
            }
        }
    }

    private fun consumeAPIResponseAddPhotos(response: Resource<AddFilesResposne>) {
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
                        editGroupViewModel.callGetFilesListApi()
                    } else {
                        showMessage(response.data.message)
                    }
                }
            }
        }
    }

    private fun consumeAPIResponseDeleteFiles(response: Resource<CommonResponse>) {
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
                        editGroupViewModel.callGetFilesListApi()
                    } else {
                        showMessage(response.data.message)
                    }
                }
            }
        }
    }


    private fun consumeAPIResponseNotify(response: Resource<CommonResponse>) {
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

                    } else {
                        showMessage(response.data.message)
                    }
                }
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
                            editGroupViewModel.strImage.value = mediaFile.path
                        }

                        editGroupViewModel.addPhotosApi()
                    }
                }
            }
        }
    }


}