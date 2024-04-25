package com.fantasy.goliath.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.view.isVisible
import com.fantasy.goliath.R
import com.fantasy.goliath.databinding.MyToolBarLayoutBinding


class MyToolBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private val binding: MyToolBarLayoutBinding

    private var visibleBackBtn: Boolean = true
    private var visibleTitle: Boolean = true
    private var visibleTeamIcon: Boolean = false
    private var visibleWallet: Boolean = true
    private var visibleNotification: Boolean = true
    private var listener: MyToolBarListener? = null


    init {
        binding = MyToolBarLayoutBinding.inflate(LayoutInflater.from(context), this, true)
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyToolBar)
        visibleTeamIcon=typedArray.getBoolean(R.styleable.MyToolBar_show_team_icon,false)
        typedArray.recycle()
        initView()
    }

    private fun initView() {
        binding.imgBack.isVisible=visibleBackBtn
        binding.txtTitle.isVisible=visibleTitle

        binding.imgTeam1.isVisible=visibleTeamIcon
        binding.imgTeam2.isVisible=visibleTeamIcon

        binding.imgMenu2.isVisible=visibleNotification
        binding.imgMenu1.isVisible=visibleWallet
        binding.imgBack.setOnClickListener { listener?.onBackPressed() }
        binding.imgMenu1.setOnClickListener { listener?.onWalletIconClick() }
        binding.imgMenu2.setOnClickListener { listener?.onNotificationsIconClick() }

    }

    fun setClickListener(listener: MyToolBarListener) {
        this.listener = listener
    }

    fun setTitle(title: String) {
        binding.txtTitle.text = title
    }

    fun getToolBarView(): MyToolBarLayoutBinding = binding

    interface MyToolBarListener {
        fun onBackPressed()
        fun onWalletIconClick()
        fun onNotificationsIconClick()
    }

}