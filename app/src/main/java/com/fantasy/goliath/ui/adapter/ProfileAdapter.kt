package com.fantasy.goliath.ui.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fantasy.goliath.databinding.ListProfileItemBinding
import com.fantasy.goliath.model.ProfileItem


class ProfileAdapter(
    mContext: Context,
    dataItem: MutableList<ProfileItem>,
    val listenerClick: (Int, String) -> Unit
) :
    RecyclerView.Adapter<ProfileAdapter.MainViewHolder>() {
    var dataList = mutableListOf<ProfileItem>()


    var mContext: Context

    init {
        this.dataList = dataItem
        this.mContext = mContext


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding =
            ListProfileItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val current = dataList[position]
        holder.bind(current)
        holder.binding.tvTitle.text = current.title
        holder.binding.tvDesc.text = current.sub_title
       /* if (current.type.equals("logout", true)) {
            holder.binding.tvTitle.setTextColor(ContextCompat.getColor(mContext, R.color.btn_color))
            holder.binding.imgNext.visibility = View.GONE
        } else {
            holder.binding.tvTitle.setTextColor(ContextCompat.getColor(mContext, R.color.colorText))
            holder.binding.imgNext.visibility = View.VISIBLE
        }*/

        holder.itemView.setOnClickListener() {
            listenerClick(position, dataList[position].type)
        }


    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class MainViewHolder(val binding: ListProfileItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(modal: ProfileItem) {
            binding.modal = modal
        }
    }


}





