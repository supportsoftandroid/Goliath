package com.fantasy.goliath.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fantasy.goliath.R
import com.fantasy.goliath.databinding.FragmentHomeBinding
import com.fantasy.goliath.databinding.FragmentNotificationsBinding
import com.fantasy.goliath.model.CommonDataItem
import com.fantasy.goliath.model.MatchDataItem
import com.fantasy.goliath.ui.adapter.MatchItemAdapter
import com.fantasy.goliath.ui.adapter.NotificationsAdapter
import com.fantasy.goliath.utility.PreferenceManager
import com.fantasy.goliath.utility.UtilsManager
import com.fantasy.goliath.viewmodal.HomeViewModel
import com.fantasy.goliath.viewmodal.NotificationsViewModel

class NotificationsFragment : Fragment() {

    private val viewModal by lazy { ViewModelProvider(this)[NotificationsViewModel::class.java] }

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentNotificationsBinding.inflate(layoutInflater)
    }

    lateinit var adapter: NotificationsAdapter
    var dataList = mutableListOf<CommonDataItem>()
    lateinit var preferenceManager: PreferenceManager
    lateinit var utilsManager: UtilsManager
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root: View = binding.root
        preferenceManager= PreferenceManager(requireActivity())
        utilsManager= UtilsManager(requireActivity())
        binding.let{ initView()
        clickListener()}
        return root
    }
    private fun clickListener() {

        binding.btnBack.setOnClickListener() {

            requireActivity().onBackPressed()

        }


    }
    private fun initView() {
        binding.viewHeader.txtTitle.text=requireActivity().getString(R.string.title_notifications)
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



    }

    private fun onAdapterClick(pos: Int, type: String) {

    }

    override fun onDestroyView() {
        super.onDestroyView()

    }
}