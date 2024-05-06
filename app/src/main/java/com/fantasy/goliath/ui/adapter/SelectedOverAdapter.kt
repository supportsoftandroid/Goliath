package com.fantasy.goliath.ui.adapter


import android.content.Context
import android.content.res.ColorStateList

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.fantasy.goliath.R
import com.fantasy.goliath.databinding.ListOverItemBinding
import com.fantasy.goliath.model.CommonDataItem
import com.fantasy.goliath.model.OverItem


class SelectedOverAdapter(
    mContext: Context,
    parentPosition: Int,
    dataItem: ArrayList<OverItem>,
    val listenerClick: (Int,Int, String) -> Unit
) :
    RecyclerView.Adapter<SelectedOverAdapter.MainViewHolder>() {
    var dataList = arrayListOf<OverItem>()


    var mContext: Context
      var parentPos:Int=-1
    var selectedPos = -1

    init {
        this.dataList = dataItem
        this.parentPos = parentPosition
        this.mContext = mContext


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding =
            ListOverItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val current = dataList[position]
        holder.bind(current)
        holder.binding.tvTitle.text = current.over_number
        val tvTitle = holder.binding.tvTitle
        tvTitle.setTextColor(ContextCompat.getColor(mContext, R.color.app_color))
        if (current.over_status.equals("available", true)) {
            holder.itemView.setOnClickListener {
                selectedPos = position
                dataList[position].is_selected = !current.is_selected
                listenerClick(parentPos,position, current.over_status)
                notifyDataSetChanged()
            }
        }
        when (current.over_status.lowercase()) {

            "completed" -> {
                setTexAndBgColor(tvTitle, R.color.colorWhite, R.color.colorRedLight)
            }

            "predicted" -> {
                setTexAndBgColor(tvTitle, R.color.colorWhite, R.color.color2EB100)


            }

            "ongoing" -> {

                setTexAndBgColor(tvTitle, R.color.colorWhite, R.color.colorRed)

            }

            /*"selected" -> {
                setTexAndBgColor(tvTitle, R.color.colorWhite, R.color.app_color)

            }*/

            "available" -> {
                if (position == selectedPos) {
                    setTexAndBgColor(tvTitle, R.color.colorWhite, R.color.app_color)
                } else {

                    tvTitle.setBackgroundResource(R.drawable.border_layout_blue_radius_5)
                }

            }

            "upcoming", "not available" -> {
                tvTitle.setTextColor(ContextCompat.getColor(mContext, R.color.colorUpcoming))
                tvTitle.setBackgroundResource(R.drawable.border_layout_blue_light_radius_5)
            }


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

    class MainViewHolder(val binding: ListOverItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(modal: OverItem) {
            binding.modal = modal
        }
    }


}





