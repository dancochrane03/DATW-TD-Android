package com.app.datwdt.view.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.datwdt.R
import com.app.datwdt.data.model.main.GroupListResponse
import com.app.datwdt.databinding.ItemExistingGroupBinding
import com.app.datwdt.implementor.RecyclerViewItemClickListener2
import kotlinx.android.synthetic.main.item_existing_group.view.*

class ExistingGrouptAdapter(
    var context: Context,
    val list: List<GroupListResponse.Result>,
    var listener: RecyclerViewItemClickListener2
) :
    RecyclerView.Adapter<ExistingGrouptAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemExistingGroupBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_existing_group, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        holder.binding.btnExistingGroup.setText(list.get(position).groupName)
        holder.binding.btnExistingGroup.setOnClickListener(View.OnClickListener {
            listener.onItemClick(position)
        })

        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(val binding: ItemExistingGroupBinding) : RecyclerView.ViewHolder(
        binding.root
    )


}