package com.fantasy.goliath.ui.adapter


import android.content.Context
import android.content.res.ColorStateList

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.fantasy.goliath.databinding.ListInningsRowItemBinding

import com.fantasy.goliath.model.InningItem

import com.fantasy.goliath.utility.printLog


class InningItemAdapter(
    mContext: Context,
    dataItem: ArrayList<InningItem>,
    val listenerClick: (Int, Int, String) -> Unit
) :
    RecyclerView.Adapter<InningItemAdapter.MainViewHolder>() {
    var dataList = arrayListOf<InningItem>()


    var mContext: Context
      var childPos=-1

    init {
        this.dataList = dataItem
        this.mContext = mContext


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding =
            ListInningsRowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val current = dataList[position]
      //  holder.setIsRecyclable(false)
        holder.bind(current)
        holder.binding.tvTitle.text = current.inning_name
        printLog("current.inning_name",current.inning_name)

        val adapter = SelectedOverAdapter(mContext, position, current.overs, { parentPos, childPos, type ->
            this.childPos=childPos
                listenerClick(parentPos, childPos, type)


            })
        holder.binding.rvList.layoutManager = GridLayoutManager(mContext, 6)
        holder.binding.rvList.adapter = adapter
        holder.binding.rvList.setNestedScrollingEnabled(false)
        if (childPos!=-1){
            holder.binding.rvList.layoutManager?.scrollToPosition(childPos)
        }

    }

    private fun setTexAndBgColor(tvTitle: TextView, colorText: Int, colorBg: Int) {
        tvTitle.setTextColor(ContextCompat.getColor(mContext, colorText))
        tvTitle.backgroundTintList =
            ColorStateList.valueOf(ContextCompat.getColor(mContext, colorBg))
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class MainViewHolder(val binding: ListInningsRowItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(modal: InningItem) {
            binding.modal = modal
        }
    }


}





