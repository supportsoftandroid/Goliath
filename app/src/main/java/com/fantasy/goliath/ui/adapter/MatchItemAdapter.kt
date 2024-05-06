package com.fantasy.goliath.ui.adapter


import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.fantasy.goliath.R
import com.fantasy.goliath.databinding.ListMatchItemBinding
import com.fantasy.goliath.model.MatchItem
import com.fantasy.goliath.utility.dateChangeInDMY
import com.fantasy.goliath.utility.loadImage


class MatchItemAdapter(
    mContext: Context,
    dataItem: MutableList<MatchItem>,
    val listenerClick: (Int, String) -> Unit
) :
    RecyclerView.Adapter<MatchItemAdapter.MainViewHolder>() {
    var dataList = mutableListOf<MatchItem>()
    var type="upcoming"
    var mContext: Context
    init {
        this.dataList = dataItem
        this.mContext = mContext
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding =
            ListMatchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val current = dataList[position]
        holder.bind(current)
        holder.binding.tvLeft.text = current.teama.short_name
        holder.binding.tvRight.text = current.teamb.short_name
        holder.binding.tvMatchType.text = current.formate
        loadImage(current.teama.logo_url, holder.binding.imgLeft)
        loadImage(current.teamb.logo_url, holder.binding.imgRight)
        if (type.equals("live",true)||type.equals("completed",true)){
            holder.binding.llMatchTime.visibility=View.GONE
            holder.binding.tvLive.visibility=View.VISIBLE
            holder.binding.tvLive.text=type
            if (type.equals("completed",true)){
                holder.binding.tvLive.setBackgroundResource(R.drawable.button_bg_green)
            }else{
                holder.binding.tvLive.setBackgroundResource(R.drawable.button_bg_red_round)
            }
        }else{
            holder.binding.llMatchTime.isVisible=true
            holder.binding.tvDay.text= dateChangeInDMY(current.match_start_date)
            holder.binding.tvTime.text=current.match_start_time
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

    class MainViewHolder(val binding: ListMatchItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(modal: MatchItem) {
            binding.modal = modal
        }
    }


}





