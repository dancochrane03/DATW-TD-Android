package com.app.datwdt.util

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class EndlessScrollRecyclerViewListener : RecyclerView.OnScrollListener() {
    // The total number of items in the dataset after the last load
    private val previousTotalItemCount = 0
    private val currentPage = 0
    override fun onScrolled(mRecyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(mRecyclerView, dx, dy)
        //        LogUtils.Print("EndlessScrollRecyclerViewListener", "onScrolled:- ");
        val mLayoutManager = (mRecyclerView
                .layoutManager as LinearLayoutManager?)!!
        val visibleItemCount = mLayoutManager.childCount
        val totalItemCount = mLayoutManager.itemCount
        val pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition()
        //        LogUtils.Print("visibleItemCount",""+visibleItemCount);
//        LogUtils.Print("totalItemCount:- ",""+totalItemCount);
//        LogUtils.Print("pastVisibleItems:- ",""+pastVisibleItems);
        onScroll(pastVisibleItems, visibleItemCount, totalItemCount)
    }

    private fun onScroll(pastVisibleItems: Int, visibleItemCount: Int, totalItemCount: Int) {
        if (pastVisibleItems + visibleItemCount >= totalItemCount) {
            onLoadMore()
        }
    }

    // Defines the process for actually loading more data based on page
    abstract fun onLoadMore()
}