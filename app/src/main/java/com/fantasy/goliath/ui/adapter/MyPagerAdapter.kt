package com.health.kharma.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import java.util.Objects

class MyPagerAdapter<T>(
    private val layoutId: Int,
    private val context: Context,
    private val list: List<T>,
    private val callback: (view: View, data: T, pos: Int) -> Unit
) : PagerAdapter() {
    override fun getCount(): Int = list.size
    override fun isViewFromObject(view: View, `object`: Any): Boolean =
        view === `object` as View

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val mLayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView: View = mLayoutInflater.inflate(layoutId, container, false)
        callback.invoke(itemView, list[position], position)
        Objects.requireNonNull(container).addView(itemView)
        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) =
        container.removeView(`object` as View)
}