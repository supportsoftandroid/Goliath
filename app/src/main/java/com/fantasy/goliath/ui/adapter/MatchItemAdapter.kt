package com.fantasy.goliath.ui.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fantasy.goliath.R
import com.fantasy.goliath.databinding.ListMatchItemBinding
import com.fantasy.goliath.model.MatchDataItem
import com.fantasy.goliath.utility.setMatchTeamViewColor


class MatchItemAdapter(
    mContext: Context,
    dataItem: MutableList<MatchDataItem>,
    val listenerClick: (Int, String) -> Unit
) :
    RecyclerView.Adapter<MatchItemAdapter.MainViewHolder>() {
    var dataList = mutableListOf<MatchDataItem>()
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
        holder.binding.tvLeft.text = current.team_a
        holder.binding.tvRight.text = current.team_b
        holder.binding.tvMatchType.text = current.match_type
        if (type.equals("live",true)||type.equals("Completed",true)){
            holder.binding.llMatchTime.visibility=View.GONE
            holder.binding.tvLive.visibility=View.VISIBLE
            holder.binding.tvLive.text=type
            if (type.equals("Completed",true)){
                holder.binding.tvLive.setBackgroundResource(R.drawable.button_bg_green)
            }else{
                holder.binding.tvLive.setBackgroundResource(R.drawable.button_bg_red_round)
            }
        }else{
            holder.binding.llMatchTime.visibility=View.VISIBLE
            holder.binding.tvLive.visibility=View.GONE
        }
        holder.itemView.setOnClickListener(){
            listenerClick(position,"")
        }
        setMatchTeamViewColor(mContext,current.team_a,holder.binding.imgLeftCircle,holder.binding.viewLeftBorder,)
        setMatchTeamViewColor(mContext,current.team_b,holder.binding.imgRightCircle,holder.binding.viewrightBorder,)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun updateMatchType(type: String) {
        this.type=type
        notifyDataSetChanged()

    }

    class MainViewHolder(val binding: ListMatchItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(modal: MatchDataItem) {
            binding.modal = modal
        }
    }


}





