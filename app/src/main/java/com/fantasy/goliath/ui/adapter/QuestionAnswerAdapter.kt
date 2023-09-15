package com.fantasy.goliath.ui.adapter


import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fantasy.goliath.R
import com.fantasy.goliath.model.QuestionAnsItem

import com.fantasy.goliath.databinding.ListQuestionAnswerItemBinding


class QuestionAnswerAdapter(
    mContext: Context,
    dataItem: MutableList<QuestionAnsItem>,
    val listenerClick: (Int, String) -> Unit
) :
    RecyclerView.Adapter<QuestionAnswerAdapter.MainViewHolder>() {
    var dataList = mutableListOf<QuestionAnsItem>()


    var mContext: Context

    init {
        this.dataList = dataItem
        this.mContext = mContext


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding =
            ListQuestionAnswerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val current = dataList[position]
        holder.bind(current)
        holder.binding.tvQuestion.text =Html.fromHtml( current.question)
        holder.binding.tvYourAnswer.text = if (current.your_answer.equals("yes")) mContext.getString(
            R.string.your_answer_yes) else  mContext.getString(
            R.string.your_answer_no)
        if (current.correct_answer.equals("")){
            holder.binding.tvYourAnswer.visibility=View.GONE
            holder.binding.imgNo.visibility=View.GONE
            holder.binding.imgNo.visibility=View.GONE
            holder.binding.tvNo.visibility=View.VISIBLE
            holder.binding.tvYes.visibility=View.VISIBLE
        }else{
            holder.binding.tvYourAnswer.visibility=View.VISIBLE
            if (current.correct_answer.equals("yes",true)){
                holder.binding.imgYes.visibility=View.VISIBLE
            }else{
                holder.binding.imgNo.visibility=View.VISIBLE

            }

            holder.binding.tvNo.visibility=View.GONE
            holder.binding.tvYes.visibility=View.GONE
        }

        if (current.your_answer.equals("yes")){
            changeTvColorAndBg(holder.binding.tvYes,holder.binding.tvNo)
        }else if (current.your_answer.equals("no")){
            changeTvColorAndBg(holder.binding.tvNo,holder.binding.tvYes)
        }
        holder.binding.tvYes.setOnClickListener(){
            dataList[position].your_answer="yes"

            notifyDataSetChanged()
        }
        holder.binding.tvNo.setOnClickListener(){
            dataList[position].your_answer="no"
            notifyDataSetChanged()
        }
    }

    private fun changeTvColorAndBg(tv1: TextView, tv2: TextView) {
        tv1.setTextColor(mContext.resources.getColor(R.color.colorWhite))
        tv1.setBackgroundResource( R.drawable.button_background_radius_20 )

        tv2.setTextColor(mContext.resources.getColor(R.color.app_color))
        tv2.setBackgroundResource( R.drawable.button_border_blue_radius_20 )
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class MainViewHolder(val binding: ListQuestionAnswerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(modal: QuestionAnsItem) {
            binding.modal = modal
        }
    }


}





