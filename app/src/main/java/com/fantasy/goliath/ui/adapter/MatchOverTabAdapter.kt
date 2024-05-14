package com.fantasy.goliath.ui.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fantasy.goliath.R
import com.fantasy.goliath.databinding.ListTabOverItemBinding
import com.fantasy.goliath.model.CommonDataItem
import com.fantasy.goliath.model.OverItem


class MatchOverTabAdapter(
    mContext: Context,
    dataItem: ArrayList<OverItem>,
    val listenerClick: (Int, String) -> Unit
) :
    RecyclerView.Adapter<MatchOverTabAdapter.MainViewHolder>() {
    var dataList = arrayListOf<OverItem>()


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
    fun update(dataItem: ArrayList<OverItem>){
        this.dataList = dataItem
        selectedPos=0
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val current = dataList[position]
        holder.bind(current)
        holder.binding.tvTitle.text ="Over ${current.over_number}"
        holder.itemView.setOnClickListener {
            selectedPos=position
            listenerClick(position,"")
            notifyDataSetChanged()
        }
        if (position==selectedPos){
            if (position==0&&dataList.size==1){
                holder.binding.clvCardMain.setBackgroundResource(R.drawable.bg_layout_blue_radius_10)
            }else if (position==0){
                holder.binding.clvCardMain.setBackgroundResource(R.drawable.bg_blue_start_tab_radius_10)
            }else if (position==dataList.size-1){
                holder.binding.clvCardMain.setBackgroundResource(R.drawable.bg_blue_end_tab_radius_10)
            }else{
                holder.binding.clvCardMain.setBackgroundColor(mContext.resources.getColor(R.color.app_color))
            }

            holder.binding.tvTitle.setTextColor(mContext.resources.getColor(R.color.colorWhite))

        }else{
            if (position==0&&dataList.size==1){
                holder.binding.clvCardMain.setBackgroundResource(R.drawable.border_layout_blue_radius_10)
            }else if (position==0){
                holder.binding.clvCardMain.setBackgroundResource(R.drawable.bg_white_start_tab_radius_10)
            }else if (position==dataList.size-1){
                holder.binding.clvCardMain.setBackgroundResource(R.drawable.bg_white_end_tab_radius_10)
            }else{
                holder.binding.clvCardMain.setBackgroundResource(R.drawable.border_blue_center_tab)
            }

            holder.binding.tvTitle.setTextColor(mContext.resources.getColor(R.color.app_color))
        }

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class MainViewHolder(val binding: ListTabOverItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(modal: OverItem) {
            binding.modal = modal
        }
    }


}





