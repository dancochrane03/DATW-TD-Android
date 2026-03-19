package com.app.datwdt.view.main

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.app.datwdt.R
import com.app.datwdt.ViewModelFactory
import com.app.datwdt.base.BaseFragment
import com.app.datwdt.constants.Constants
import com.app.datwdt.constants.Constants.packageName
import com.app.datwdt.data.main.Resource
import com.app.datwdt.data.model.main.AddFilesResposne
import com.app.datwdt.databinding.FragmentSuccessCreateGroupBinding
import com.app.datwdt.util.Utils
import com.app.datwdt.viewmodel.main.SuccessCreateGroupViewModel
import com.jaiselrahman.filepicker.activity.FilePickerActivity
import com.jaiselrahman.filepicker.config.Configurations
import com.jaiselrahman.filepicker.model.MediaFile
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.io.File
import java.util.jar.Manifest
import javax.inject.Inject

class SuccessCreateGroupFragment : BaseFragment(), View.OnClickListener {

    private lateinit var mBinding: FragmentSuccessCreateGroupBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var successCreateGroupViewModel: SuccessCreateGroupViewModel
    private var cameraImageUri: Uri? = null

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
                openCameraWithPermission()
            }
        }
    }
    private fun showImagePickerDialog() {
        val options = arrayOf("Camera", "Gallery")
        AlertDialog.Builder(requireContext())
            .setTitle("Select Image")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> openCamera()
                    1 -> openGallery()
                }
            }
            .show()
    }
    private fun openGallery() {
        galleryLauncher.launch(
            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
        )
    }
    private fun openCamera() {
        val file = File(requireContext().cacheDir, "camera_${System.currentTimeMillis()}.jpg")
        cameraImageUri = FileProvider.getUriForFile(
            requireContext(),
            requireContext().packageName + ".provider",
            file
        )
        cameraLauncher.launch(cameraImageUri)
    }
    private fun openCameraWithPermission() {
        val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            listOf(
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.READ_MEDIA_IMAGES,
                android.Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED,
            )
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            listOf(
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.READ_MEDIA_IMAGES
            )
        }else{
            listOf(
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
            )
        }

        Dexter.withContext(requireContext())
            .withPermissions(permissions)
            .withListener(object : MultiplePermissionsListener {

                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    report?.let {
                        if (report.areAllPermissionsGranted()) {
                            showImagePickerDialog()
                        }
                        // ❗ permanently denied → open settings
                        if (report.isAnyPermissionPermanentlyDenied) {
                            showSettingsDialog()
                        } else if (!report.areAllPermissionsGranted()) {
                            Toast.makeText(
                                requireContext(),
                                "Permission required to continue",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }
            })
            .withErrorListener {
                Toast.makeText(requireContext(), "Error occurred!", Toast.LENGTH_SHORT).show()
            }
            .check()
    }
    private fun showSettingsDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Permission Required")
            .setMessage("Some permissions are permanently denied. Please enable them in Settings.")
            .setPositiveButton("Open Settings") { _, _ ->
                val intent = Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.fromParts("package", requireContext().packageName, null)
                )
                startActivity(intent)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                Log.d("TAG", "Gallery Image: $uri")
                val filePath = Utils.getFilePathFromUri(uri!!,requireContext())
                if (!filePath.isNullOrEmpty()) {
                    successCreateGroupViewModel.strImage.value = filePath
                    // 👉 API call
                    successCreateGroupViewModel.addPhotosApi()
                }
            }
        }
    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success && cameraImageUri != null) {
                Log.d("TAG", "Camera Image: $cameraImageUri")
                val filePath = Utils.getFilePathFromUri(cameraImageUri!!,requireContext())
                if (!filePath.isNullOrEmpty()) {
                    successCreateGroupViewModel.strImage.value = filePath
                    // 👉 API call
                    successCreateGroupViewModel.addPhotosApi()
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