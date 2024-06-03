package com.fantasy.goliath.ui.adapter


import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.fantasy.goliath.R

import com.fantasy.goliath.databinding.ListAwardItemBinding

import com.fantasy.goliath.model.LeaderBoardItem



class AwardAdapter(
    mContext: Context,
    dataItem: ArrayList<LeaderBoardItem>,
    val listenerClick: (Int, String) -> Unit
) :
    RecyclerView.Adapter<AwardAdapter.MainViewHolder>() {
    var dataList = mutableListOf<LeaderBoardItem>()


    var mContext: Context

    init {
        this.dataList = dataItem
        this.mContext = mContext


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding =
            ListAwardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val current = dataList[position]
        holder.bind(current)
        holder.binding.tvName.text = current.user.full_name.capitalize()
        holder.binding.tvWinning.text =if (TextUtils.isEmpty(current.TotalGamesPlayed)) "${current.TotalGamesWinning}" else "${current.TotalGamesPlayed}"
        val srNo=position+1
        holder.binding.tvNo.text = srNo.toString()+"."
        if (position<3){
            if (position==0){
                holder.binding.imgProduct.setImageResource(R.drawable.ic_price_gold)
            }else if (position==1){
                holder.binding.imgProduct.setImageResource(R.drawable.ic_price_sliver)
            }else  {
                holder.binding.imgProduct.setImageResource(R.drawable.ic_price_bronz)
            }
            holder.binding.imgProduct.visibility=View.VISIBLE
            holder.binding.tvNo.visibility=View.GONE
        }else{
            holder.binding.imgProduct.visibility=View.GONE
            holder.binding.tvNo.visibility=View.VISIBLE

        }

        holder.itemView.setOnClickListener() {
            listenerClick(position, dataList[position].TotalGamesWinning)
        }


    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun updateMatchType(type: String) {

    }

    class MainViewHolder(val binding: ListAwardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(modal: LeaderBoardItem) {
            binding.modal = modal
        }
    }


}





