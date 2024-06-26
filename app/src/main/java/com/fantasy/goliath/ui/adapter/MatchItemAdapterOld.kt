package com.fantasy.goliath.ui.adapter


import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater

import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.fantasy.goliath.R

import com.fantasy.goliath.databinding.ListMatchItemOldBinding
import com.fantasy.goliath.model.MatchItem

import com.fantasy.goliath.utility.getMatchDate
import com.fantasy.goliath.utility.getMatchTime
import com.fantasy.goliath.utility.loadImage


class MatchItemAdapterOld(
    mContext: Context,
    dataItem: MutableList<MatchItem>,
    val listenerClick: (Int, String) -> Unit
) :
    RecyclerView.Adapter<MatchItemAdapterOld.MainViewHolder>() {
    var dataList = mutableListOf<MatchItem>()
    var type="upcoming"
    var mContext: Context
    init {
        this.dataList = dataItem
        this.mContext = mContext
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding =
            ListMatchItemOldBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val current = dataList[position]
        holder.bind(current)
        holder.binding.tvTournamentName.text = current.competiton_name
        holder.binding.tvLeft.text = current.teama.short_name
        holder.binding.tvRight.text = current.teamb.short_name
        holder.binding.tvMatchType.text = current.formate
        loadImage(current.teama.logo_url, holder.binding.imgLeft)
        loadImage(current.teamb.logo_url, holder.binding.imgRight)
        if (type.equals("live",true)||type.equals("completed",true)){
            holder.binding.llMatchTime.isVisible=false
            holder.binding.tvLive.isVisible=true
            holder.binding.tvLeftScore.isVisible=true
            holder.binding.tvRightScore.isVisible=true
            holder.binding.tvLive.text=type.uppercase()
            holder.binding.tvLeftScore.text=current.teama.scores_full
            holder.binding.tvRightScore.text=current.teamb.scores_full
            if (type.equals("completed",true)){
                holder.binding.tvLive.setBackgroundResource(R.drawable.button_bg_green)
            }else{
                holder.binding.tvLive.setBackgroundResource(R.drawable.button_bg_red_round)
            }
        }else{
            holder.binding.tvLeftScore.isVisible=false
            holder.binding.tvRightScore.isVisible=false
            holder.binding.llMatchTime.isVisible=true
            holder.binding.tvDay.text= getMatchDate("${current.match_start_date} ${ current.match_start_time }")
            holder.binding.tvTime.text=getMatchTime("${current.match_start_date} ${ current.match_start_time }")
            holder.binding.tvLive.isVisible=false
        }
        holder.itemView.setOnClickListener(){
            listenerClick(position,"")
        }
       // setMatchTeamViewColor(mContext,current.teama_name,holder.binding.imgLeftCircle,holder.binding.viewLeftBorder,)
       // setMatchTeamViewColor(mContext,current.teamb_name,holder.binding.imgRightCircle,holder.binding.viewrightBorder,)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateMatchType(type: String) {
        this.type=type
        notifyDataSetChanged()

    }

    class MainViewHolder(val binding: ListMatchItemOldBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(modal: MatchItem) {
            binding.modal = modal
        }
    }


}





