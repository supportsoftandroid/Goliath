package com.fantasy.goliath.utility

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class WrapContentLinearLayoutManager(context: Context) : LinearLayoutManager(context) {
    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        try {
            super.onLayoutChildren(recycler, state)
        } catch (e: IndexOutOfBoundsException) {
            e.printStackTrace()
        }
    }

    override fun onMeasure(recycler: RecyclerView.Recycler, state: RecyclerView.State, widthSpec: Int, heightSpec: Int) {
        val view = recycler.getViewForPosition(0)
        if (view != null) {
            measureChild(view, widthSpec, heightSpec)
            val measuredWidth = View.MeasureSpec.getSize(widthSpec)
            val measuredHeight = view.measuredHeight * itemCount
            setMeasuredDimension(measuredWidth, measuredHeight)
        } else {
            super.onMeasure(recycler, state, widthSpec, heightSpec)
        }
    }
}
