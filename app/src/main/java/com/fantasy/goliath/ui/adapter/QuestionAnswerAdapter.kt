package com.fantasy.goliath.ui.adapter


import android.content.Context
import android.text.Html
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fantasy.goliath.R
import com.fantasy.goliath.databinding.ListQuestionAnswerItemBinding
import com.fantasy.goliath.model.QuestionAnsItem
import com.fantasy.goliath.utility.printLog


class QuestionAnswerAdapter(
    mContext: Context,
    dataItem: ArrayList<QuestionAnsItem>,
    val listenerClick: (Int, String) -> Unit
) :
    RecyclerView.Adapter<QuestionAnswerAdapter.MainViewHolder>() {
    var dataList = arrayListOf<QuestionAnsItem>()


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
        holder.setIsRecyclable(false)
        holder.bind(current)

        printLog("position",position.toString())
        val count=position+1
        holder.binding.tvQuestion.text =Html.fromHtml("${count}. ${ current.question}")
        holder.binding.tvYourAnswer.text =Html.fromHtml( if (!TextUtils.isEmpty(current.your_answer)&&current.your_answer.equals("1")) mContext.getString(
            R.string.your_answer_yes) else  mContext.getString(
            R.string.your_answer_no))
        if (TextUtils.isEmpty(current.correct_answer)){
            holder.binding.tvYourAnswer.visibility=View.GONE
            holder.binding.imgNo.visibility=View.GONE
            holder.binding.imgNo.visibility=View.GONE
            holder.binding.tvNo.visibility=View.VISIBLE
            holder.binding.tvYes.visibility=View.VISIBLE
        }else{
            holder.binding.tvYourAnswer.visibility=View.VISIBLE
            if (current.correct_answer.equals("1",true)){
                holder.binding.imgYes.visibility=View.VISIBLE
            }else{
                holder.binding.imgNo.visibility=View.VISIBLE
            }
            holder.binding.tvNo.visibility=View.GONE
            holder.binding.tvYes.visibility=View.GONE
        }

        updateTextAnswertext(holder,current)
        holder.binding.tvYes.setOnClickListener(){
            current.your_answer="1"
            dataList[position]=current
            updateTextAnswertext(holder,current)
            listenerClick(position, dataList[position].your_answer)
           // notifyDataSetChanged()
        }
        holder.binding.tvNo.setOnClickListener(){
            current.your_answer= "2"
            dataList[position]=current
            updateTextAnswertext(holder,current)
            listenerClick(position, dataList[position].your_answer)

        }
    }

    private fun updateTextAnswertext(holder: QuestionAnswerAdapter.MainViewHolder, current: QuestionAnsItem) {
        if (!TextUtils.isEmpty(current.your_answer)&&current.your_answer.equals("1")){
            changeTvColorAndBg(holder.binding.tvYes,holder.binding.tvNo)
        }else if (!TextUtils.isEmpty(current.your_answer)&&current.your_answer.equals("2")){
            changeTvColorAndBg(holder.binding.tvNo,holder.binding.tvYes)
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





