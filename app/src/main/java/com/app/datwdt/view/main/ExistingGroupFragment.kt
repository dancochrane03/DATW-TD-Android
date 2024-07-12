package com.app.datwdt.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.datwdt.R
import com.app.datwdt.ViewModelFactory
import com.app.datwdt.base.BaseFragment
import com.app.datwdt.constants.Constants
import com.app.datwdt.data.main.Resource
import com.app.datwdt.data.model.CommonResponse
import com.app.datwdt.data.model.main.GroupListResponse
import com.app.datwdt.databinding.FragmentExistingGroupBinding
import com.app.datwdt.implementor.RecyclerViewItemClickListener2
import com.app.datwdt.view.main.adapter.ExistingGrouptAdapter
import com.app.datwdt.viewmodel.main.ExistingGroupViewModel
import javax.inject.Inject


class ExistingGroupFragment() : BaseFragment(), View.OnClickListener,
    RecyclerViewItemClickListener2 {

    private lateinit var mBinding: FragmentExistingGroupBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var existingGroupViewModel: ExistingGroupViewModel

    //adapter
    lateinit var existingGrouptAdapter: ExistingGrouptAdapter

    var existingGroupList: ArrayList<GroupListResponse.Result> =
        ArrayList<GroupListResponse.Result>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentExistingGroupBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun initViewModel() {
        existingGroupViewModel = viewModelFactory.create(ExistingGroupViewModel::class.java)
        mBinding.setLifecycleOwner(this)
        existingGroupViewModel.initialize(requireActivity(), mBinding)
        mBinding.existingGroupViewModel = existingGroupViewModel
        existingGroupViewModel.callGetGroupListApi()
    }

    override fun observeViewModel() {
        existingGroupViewModel.getGroupListliveData()
            .observe(this) { response: Resource<GroupListResponse> ->
                consumeAPIResponse(
                    response
                )
            }

        existingGroupViewModel.notifyliveData()
            .observe(this) { response: Resource<CommonResponse> ->
                consumeAPIResponseNotify(
                    response
                )
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.header.tvTitleMenu.setText(getString(R.string.text_existing_group))
        setAdapter()
        mBinding.btnNotifyCompletion.setOnClickListener(this)
        mBinding.header.btnMenu.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnNotifyCompletion -> {
                existingGroupViewModel.notifyApi()
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
        existingGroupList = ArrayList()

        mBinding.rvExistingGroup.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        existingGrouptAdapter =
            ExistingGrouptAdapter(
                requireContext(),
                existingGroupList, this
            )
        mBinding.rvExistingGroup.setAdapter(existingGrouptAdapter)
    }

    private fun consumeAPIResponse(response: Resource<GroupListResponse>) {
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
                        existingGroupList.addAll(response.data.result as ArrayList<GroupListResponse.Result>)
                        existingGrouptAdapter.notifyDataSetChanged()
                        showMessage(response.data.message)
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


    override fun onItemClick(position: Int) {

        var fragment = EditGroupFragment()
        var bundle = Bundle()
        bundle.putString(
            Constants.group_id,
            existingGroupList.get(position).id
        )
        bundle.putString(
            Constants.group_name,
            existingGroupList.get(position).groupName
        )
        fragment.arguments = bundle

        replaceFragment(fragment, true, false, "EditGroupFragment")

    }


}