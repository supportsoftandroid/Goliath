package com.fantasy.goliath.ui.adapter


import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.fantasy.goliath.R
import com.fantasy.goliath.databinding.ListTransactionItemBinding
import com.fantasy.goliath.model.TransactionsItem
import com.fantasy.goliath.utility.dateChangeInDMY
import com.fantasy.goliath.utility.dateChangeTimeTransaction


class TransactionAdapter(
    mContext: Context,
    dataItem: ArrayList<TransactionsItem>,
    val listenerClick: (Int, String) -> Unit
) :
    RecyclerView.Adapter<TransactionAdapter.MainViewHolder>() {
    var dataList = mutableListOf<TransactionsItem>()
    var type="debit"


    var mContext: Context

    init {
        this.dataList = dataItem
        this.mContext = mContext


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding =
            ListTransactionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val current = dataList[position]
        holder.bind(current)
        holder.binding.tvTitle.text = current.transaction_type.capitalize()
        holder.binding.tvPrice.text = current.amount_show
        holder.binding.tvDate.text = dateChangeTimeTransaction(current.created_at)

         if (current.payment_mode.equals("credit", true)) {
             holder.binding.tvTitle.text = mContext.getString(R.string.deposit)
             holder.binding.imgArrow.rotation=0f
            holder.binding.tvTitle.setTextColor(ContextCompat.getColor(mContext, R.color.color2EB100))
             holder.binding.imgProduct.backgroundTintList= ColorStateList.valueOf(ContextCompat.getColor(mContext,  R.color.color2EB100))
            holder.binding.imgArrow.setImageResource(R.drawable.ic_deposit)
        } else {
             holder.binding.imgArrow.setImageResource(R.drawable.ic_debit)
             holder.binding.tvTitle.setTextColor(ContextCompat.getColor(mContext, R.color.colorRed))
             holder.binding.imgProduct.backgroundTintList= ColorStateList.valueOf(ContextCompat.getColor(mContext,  R.color.colorRed))
             holder.binding.imgArrow.backgroundTintList= ColorStateList.valueOf(ContextCompat.getColor(mContext,  R.color.colorRed))
             holder.binding.imgArrow.rotation=90f
         }

        holder.itemView.setOnClickListener() {
            listenerClick(position, dataList[position].payment_mode)
        }


    }

    override fun getItemCount(): Int {
        return dataList.size
    }



    class MainViewHolder(val binding: ListTransactionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(modal: TransactionsItem) {
            binding.modal = modal
        }
    }


}





