package com.fantasy.goliath.ui.adapter


import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fantasy.goliath.R
import com.fantasy.goliath.model.CommonDataItem
import com.fantasy.goliath.databinding.ListTabOverItemBinding


class MatchOverTabAdapter(
    mContext: Context,
    dataItem: MutableList<CommonDataItem>,
    val listenerClick: (Int, String) -> Unit
) :
    RecyclerView.Adapter<MatchOverTabAdapter.MainViewHolder>() {
    var dataList = mutableListOf<CommonDataItem>()


    var mContext: Context
    var selectedPos=0

    init {
        this.dataList = dataItem
        this.mContext = mContext


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding =
            ListTabOverItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val current = dataList[position]
        holder.bind(current)
        holder.binding.tvTitle.text =current.title
        if (position==selectedPos){
            holder.binding.clvCardMain.setBackgroundColor(mContext.resources.getColor(R.color.app_color))
            holder.binding.tvTitle.setTextColor(mContext.resources.getColor(R.color.colorWhite))

        }else{
            holder.binding.clvCardMain.setBackgroundColor(mContext.resources.getColor(R.color.colorWhite))
            holder.binding.tvTitle.setTextColor(mContext.resources.getColor(R.color.app_color))
        }

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class MainViewHolder(val binding: ListTabOverItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(modal: CommonDataItem) {
            binding.modal = modal
        }
    }


}





