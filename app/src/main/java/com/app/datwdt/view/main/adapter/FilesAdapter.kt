package com.app.datwdt.view.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.datwdt.R
import com.app.datwdt.base.BaseActivity
import com.app.datwdt.constants.Constants
import com.app.datwdt.data.model.FilesListResponse
import com.app.datwdt.databinding.ItemEditGroupBinding
import com.app.datwdt.implementor.RecyclerViewItemClickListener


class FilesAdapter(
    var context: Context,
    val list: List<FilesListResponse.Result>,
    var listener: RecyclerViewItemClickListener
) :
    RecyclerView.Adapter<FilesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemEditGroupBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_edit_group, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        BaseActivity.setDataToView(
            context,
            list.get(position).filePath,
            holder.binding.ivLogo,
            R.drawable.ic_logo
        )

        holder.binding.ivDelete.setOnClickListener(View.OnClickListener {
            listener.onItemClick(position, Constants.ITEM_CLICK, holder.binding.ivDelete)
        })
        holder.binding.ivLogo.setOnClickListener(View.OnClickListener {
            listener.onItemClick(position, Constants.ITEM_CLICK, holder.binding.ivLogo)
        })



        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(val binding: ItemEditGroupBinding) : RecyclerView.ViewHolder(
        binding.root
    ), View.OnClickListener {
        override fun onClick(view: View) {
            listener.onItemClick(layoutPosition, Constants.ITEM_CLICK, view)
        }
    }


}