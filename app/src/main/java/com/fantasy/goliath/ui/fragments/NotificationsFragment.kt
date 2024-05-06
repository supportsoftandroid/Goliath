package com.fantasy.goliath.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fantasy.goliath.R
import com.fantasy.goliath.databinding.FragmentNotificationsBinding
import com.fantasy.goliath.model.CommonDataItem
import com.fantasy.goliath.ui.adapter.NotificationsAdapter
import com.fantasy.goliath.ui.base.BaseFragment
import com.fantasy.goliath.utility.PreferenceManager
import com.fantasy.goliath.utility.UtilsManager
import com.fantasy.goliath.viewmodal.NotificationsViewModel

class NotificationsFragment : BaseFragment() {

    private val viewModal by lazy { ViewModelProvider(this)[NotificationsViewModel::class.java] }

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentNotificationsBinding.inflate(layoutInflater)
    }

    lateinit var adapter: NotificationsAdapter
    var dataList = mutableListOf<CommonDataItem>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       super.onCreateView(inflater, container, savedInstanceState)

        binding.let{ initView()
        clickListener()}
        return binding.root
    }
    private fun clickListener() {
        binding.viewHeader.getToolBarView().imgMenu2.isVisible=false
        binding.viewHeader.setClickListener(this)


    }
    private fun initView() {
        binding.viewHeader.setTitle(requireActivity().getString(R.string.title_notifications))
        dataList.clear()


        dataList.add(CommonDataItem("Great time to join contest","RCB",false))
        dataList.add(CommonDataItem("Great time to join contest","RCB",false))
        dataList.add(CommonDataItem("Great time to join contest","RCB",false))
        dataList.add(CommonDataItem("Great time to join contest","RCB",false))
        dataList.add(CommonDataItem("Great time to join contest","RCB",false))
        dataList.add(CommonDataItem("Great time to join contest","RCB",false))
        dataList.add(CommonDataItem("Great time to join contest","RCB",false))
        dataList.add(CommonDataItem("Great time to join contest","RCB",false))
        adapter = NotificationsAdapter(requireActivity(), dataList, { pos, type -> onAdapterClick(pos, type) })
        binding.viewBody.rvList.layoutManager = LinearLayoutManager(requireActivity())
        binding.viewBody.rvList.adapter = adapter

        binding.viewBody.tvMessage.isVisible = false

    }

    private fun onAdapterClick(pos: Int, type: String) {

    }

    override fun onDestroyView() {
        super.onDestroyView()

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onWalletIconClick() {
        super.onWalletIconClick()
    }
}