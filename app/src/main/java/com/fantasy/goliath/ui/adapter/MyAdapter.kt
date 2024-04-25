package com.health.kharma.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class MyAdapter<T>(
    private val layoutId: Int,
    private val list: List<T>,
    private val callback: (view: View, data: T, pos: Int) -> Unit
) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(LayoutInflater.from(parent.context).inflate(layoutId, parent, false))

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) =
        callback.invoke(holder.itemView, list[position], position)

    class MyViewHolder(itemView: View) : ViewHolder(itemView)

}