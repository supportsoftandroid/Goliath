package com.fantasy.goliath.ui.adapter


import android.content.Context
import android.text.Html
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.fantasy.goliath.R
import com.fantasy.goliath.databinding.ListQuestionAnswerItemBinding
import com.fantasy.goliath.model.QuestionAnsItem
import com.fantasy.goliath.utility.Constants.RESULT_WIN
import com.fantasy.goliath.utility.getHTMLFormatText


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
            ListQuestionAnswerItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val current = dataList[position]
         // holder.setIsRecyclable(true)
        holder.bind(current)
        val count = position + 1
        holder.binding.tvQuestion.text = getHTMLFormatText("${count}. ${current.question}")
        val answer =  if (!TextUtils.isEmpty(current.your_answer)
            && current.your_answer.equals("1")) mContext.getString(
            R.string.yes
        ) else mContext.getString(
            R.string.no
        )
        holder.binding.tvAnswer.text = getHTMLFormatText(answer)

        if (TextUtils.isEmpty(current.your_answer) ) {
            holder.binding.llYourAnswer.isVisible = false
            holder.binding.imgNo.isVisible = false
            holder.binding.imgNo.isVisible = false
            holder.binding.tvNo.isVisible = true
            holder.binding.tvYes.isVisible = true
        }else  if (!TextUtils.isEmpty(current.your_result)||current.your_result.equals("nd",true)) {
            holder.binding.llYourAnswer.isVisible = true
            holder.binding.imgNo.isVisible = false
            holder.binding.imgNo.isVisible = false
            holder.binding.tvNo.isVisible = false
            holder.binding.tvYes.isVisible = false
        } else {
            holder.binding.llYourAnswer.isVisible = true
            holder.binding.imgYes.isVisible = false
            holder.binding.imgNo.isVisible = false
            if (!TextUtils.isEmpty(current.your_result)&&!current.your_result.equals("nd",true)) {
                if (current.your_result.equals(RESULT_WIN, true)) {
                    holder.binding.imgYes.isVisible = true
                } else {
                    holder.binding.imgNo.isVisible = true
                }
            }
            holder.binding.tvNo.isVisible = false
            holder.binding.tvYes.isVisible = false
        }
        updateTextAnswertext(holder, current)
        updateTextAnswertext(holder, current)
        holder.binding.tvYes.setOnClickListener() {
            current.your_answer = "1"
            dataList[position] = current
            updateTextAnswertext(holder, current)
            listenerClick(position, dataList[position].your_answer)
            // notifyDataSetChanged()
        }
        holder.binding.tvNo.setOnClickListener() {
            current.your_answer = "2"
            dataList[position] = current
            updateTextAnswertext(holder, current)
            listenerClick(position, dataList[position].your_answer)

        }
    }

    private fun updateTextAnswertext(
        holder: QuestionAnswerAdapter.MainViewHolder,
        current: QuestionAnsItem
    ) {
        if (TextUtils.isEmpty(current.your_answer)) {
            holder.binding.tvYes.setTextColor(mContext.resources.getColor(R.color.app_color))
            holder.binding.tvYes.setBackgroundResource(R.drawable.button_border_blue_radius_20)
            holder.binding.tvNo.setTextColor(mContext.resources.getColor(R.color.app_color))
            holder.binding.tvNo.setBackgroundResource(R.drawable.button_border_blue_radius_20)
        }else if (  current.your_answer.equals("1")) {
            changeTvColorAndBg(holder.binding.tvYes, holder.binding.tvNo)
        } else if ( current.your_answer.equals("2")) {
            changeTvColorAndBg(holder.binding.tvNo, holder.binding.tvYes)
        }

    }

    private fun changeTvColorAndBg(tv1: TextView, tv2: TextView) {
        tv1.setTextColor(mContext.resources.getColor(R.color.colorWhite))
        tv1.setBackgroundResource(R.drawable.button_background_radius_20)

        tv2.setTextColor(mContext.resources.getColor(R.color.app_color))
        tv2.setBackgroundResource(R.drawable.button_border_blue_radius_20)
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





