package com.fantasy.goliath.ui.adapter


import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fantasy.goliath.R
import com.fantasy.goliath.databinding.ListInningTabRowItemBinding
import com.fantasy.goliath.databinding.ListTabOverItemBinding
import com.fantasy.goliath.model.CommonDataItem
import com.fantasy.goliath.model.InningItem
import com.fantasy.goliath.model.OverItem


class InningTabAdapter(
    mContext: Context,
    dataItem: ArrayList<InningItem>,
    val listenerClick: (Int, String) -> Unit
) :
    RecyclerView.Adapter<InningTabAdapter.MainViewHolder>() {
    var dataList = arrayListOf<InningItem>()


    var mContext: Context
    var selectedPos=0

    init {
        this.dataList = dataItem
        this.mContext = mContext
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding =
            ListInningTabRowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }
    fun update(dataItem: ArrayList<InningItem>){
        this.dataList = dataItem
        selectedPos=0
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val current = dataList[position]
        holder.bind(current)
        holder.binding.tvTitle.text =current.inning_name
        if (position != selectedPos) {
            holder.binding.clvCardMain.setBackgroundResource(R.drawable.border_layout_blue_radius_10)
            holder.binding.tvTitle.setTextColor(mContext.resources.getColor(R.color.app_color))
        } else {
            holder.binding.tvTitle.setTextColor(mContext.resources.getColor(R.color.colorWhite))
            holder.binding.clvCardMain.setBackgroundResource(R.drawable.bg_layout_blue_radius_10)
        }
        holder.itemView.setOnClickListener {
            selectedPos=position
            listenerClick(position,"")
            notifyDataSetChanged()
        }


    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class MainViewHolder(val binding: ListInningTabRowItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(modal: InningItem) {
            binding.modal = modal
        }
    }


}





