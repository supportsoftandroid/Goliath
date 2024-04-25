package com.fantasy.goliath.ui.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fantasy.goliath.databinding.ListNotificationItemBinding
import com.fantasy.goliath.model.CommonDataItem


class NotificationsAdapter(
    mContext: Context,
    dataItem: MutableList<CommonDataItem>,
    val listenerClick: (Int, String) -> Unit
) :
    RecyclerView.Adapter<NotificationsAdapter.MainViewHolder>() {
    var dataList = mutableListOf<CommonDataItem>()


    var mContext: Context

    init {
        this.dataList = dataItem
        this.mContext = mContext


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding =
            ListNotificationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val current = dataList[position]
        holder.bind(current)
        holder.binding.tvTitle.text = current.title



        holder.itemView.setOnClickListener() {
            listenerClick(position, dataList[position].type)
        }


    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class MainViewHolder(val binding: ListNotificationItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(modal: CommonDataItem) {
            binding.modal = modal
        }
    }


}





