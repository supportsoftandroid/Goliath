package com.fantasy.goliath.ui.adapter


import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fantasy.goliath.model.CommonDataItem
import com.fantasy.goliath.databinding.ListHowToPlayItemBinding


class MatchItemAdapter(
    mContext: Context,
    dataItem: MutableList<CommonDataItem>,
    val listenerClick: (Int, String) -> Unit
) :
    RecyclerView.Adapter<MatchItemAdapter.MainViewHolder>() {
    var dataList = mutableListOf<CommonDataItem>()
    var type="upcoming"
    var mContext: Context

    init {
        this.dataList = dataItem
        this.mContext = mContext
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding =
            ListHowToPlayItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val current = dataList[position]
        holder.bind(current)
        holder.binding.tvTitle.text =Html.fromHtml( current.title)

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun updateMatchType(type: String) {
        this.type=type
        notifyDataSetChanged()

    }

    class MainViewHolder(val binding: ListHowToPlayItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(modal: CommonDataItem) {
            binding.modal = modal
        }
    }


}





