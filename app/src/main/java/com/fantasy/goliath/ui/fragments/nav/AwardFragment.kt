package com.fantasy.goliath.ui.fragments.nav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fantasy.goliath.R
import com.fantasy.goliath.databinding.FragmentAwardBinding
import com.fantasy.goliath.model.CommonDataItem
import com.fantasy.goliath.ui.activities.MainActivity
import com.fantasy.goliath.ui.adapter.AwardAdapter
import com.fantasy.goliath.ui.adapter.NotificationsAdapter
import com.fantasy.goliath.ui.fragments.NotificationsFragment
import com.fantasy.goliath.utility.StaticData

import com.fantasy.goliath.viewmodal.AwardViewModel

class AwardFragment : Fragment() {
    private val viewModal by lazy { ViewModelProvider(this)[AwardViewModel::class.java] }

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentAwardBinding.inflate(layoutInflater)
    }
    lateinit var adapter: AwardAdapter
    var dataList = mutableListOf<CommonDataItem>()
    var type=""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root: View = binding.root
        binding.let {
            initView()
            clickListener()
        }

        return root
    }
    private fun clickListener() {

       /* binding.viewHeader.imgProfile.setOnClickListener() {

            MainActivity.navProfileTab()

        }*/
        binding.viewHeader.imgMenu2.setOnClickListener() {

            MainActivity.hideNavigationTab()
            StaticData.backStackAddFragment(
                requireActivity(),
                NotificationsFragment()
            )

        }

    }

    private fun initView() {
        binding.viewHeader.txtTitle.text=requireActivity().getString(R.string.leaderboard)
        dataList.clear()


        dataList.add(CommonDataItem("You","RCB",false))
        dataList.add(CommonDataItem("Aadhith","RCB",false))
        dataList.add(CommonDataItem("Avni","RCB",false))
        dataList.add(CommonDataItem("Mehar","RCB",false))
        dataList.add(CommonDataItem("Bhavesh","RCB",false))
        dataList.add(CommonDataItem("Jigar Patel","RCB",false))
        dataList.add(CommonDataItem("Preetesh","RCB",false))
        dataList.add(CommonDataItem("Bhavesh","RCB",false))
        dataList.add(CommonDataItem("Jigar Patel","RCB",false))
        dataList.add(CommonDataItem("Preetesh","RCB",false))
        dataList.add(CommonDataItem("Bhavesh","RCB",false))
        dataList.add(CommonDataItem("Jigar Patel","RCB",false))
        dataList.add(CommonDataItem("Preetesh","RCB",false))
        adapter = AwardAdapter(requireActivity(), dataList, { pos, type -> onAdapterClick(pos, type) })
        binding.viewBody.rvList.layoutManager = LinearLayoutManager(requireActivity())
        binding.viewBody.rvList.adapter = adapter


        binding.rgStatus.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rbDaily -> {
                    binding.rbDaily.setTextColor(ContextCompat.getColor(requireActivity(), R.color.app_color))
                    binding.rbWeekly.setTextColor(ContextCompat.getColor(requireActivity(), R.color.textPlaceHolder))
                    binding.rbMonthly.setTextColor(ContextCompat.getColor(requireActivity(), R.color.textPlaceHolder))
                    type = "daily"
                    adapter.updateMatchType(type)

                }
                R.id.rbWeekly -> {
                    binding.rbWeekly.setTextColor(ContextCompat.getColor(requireActivity(), R.color.app_color))
                    binding.rbDaily.setTextColor(ContextCompat.getColor(requireActivity(), R.color.textPlaceHolder))
                    binding.rbMonthly.setTextColor(ContextCompat.getColor(requireActivity(), R.color.textPlaceHolder))

                    type = "weekly"
                    adapter.updateMatchType(type)

                }
                R.id.rbMonthly -> {
                    binding.rbMonthly.setTextColor(ContextCompat.getColor(requireActivity(), R.color.app_color))
                    binding.rbDaily.setTextColor(ContextCompat.getColor(requireActivity(), R.color.textPlaceHolder))
                    binding.rbWeekly.setTextColor(ContextCompat.getColor(requireActivity(), R.color.textPlaceHolder))

                    type = "weekly"
                    adapter.updateMatchType(type)

                }
            }

        }
    }

    private fun onAdapterClick(pos: Int, type: String) {

    }
    override fun onDestroyView() {
        super.onDestroyView()

    }
}