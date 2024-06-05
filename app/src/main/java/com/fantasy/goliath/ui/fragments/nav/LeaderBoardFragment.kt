package com.fantasy.goliath.ui.fragments.nav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fantasy.goliath.R
import com.fantasy.goliath.databinding.FragmentAwardBinding

import com.fantasy.goliath.model.LeaderBoardItem
import com.fantasy.goliath.ui.adapter.AwardAdapter
import com.fantasy.goliath.ui.base.BaseFragment
import com.fantasy.goliath.ui.fragments.NotificationsFragment
import com.fantasy.goliath.ui.fragments.WalletDetailsFragment

import com.fantasy.goliath.viewmodal.LeaderBoardViewModel
import com.google.gson.JsonObject

class LeaderBoardFragment : BaseFragment() {
    private val viewModal by lazy { ViewModelProvider(this)[LeaderBoardViewModel::class.java] }

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentAwardBinding.inflate(layoutInflater)
    }
    lateinit var adapter: AwardAdapter
    var dataList = ArrayList<LeaderBoardItem>()
    var type="winning"//winning,most_played
    var time_period="day"//day,month,year

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        val root: View = binding.root
        binding.let {
            initView()
            clickListener()
            callListAPI()
        }

        return root
    }
    private fun clickListener() {


        binding.viewHeader.imgMenu2.setOnClickListener() {


            addFragmentToBackStack(

                NotificationsFragment()
            )

        }
        binding.viewHeader.imgMenu1.setOnClickListener() {


            addFragmentToBackStack(

                WalletDetailsFragment()
            )

        }

    }

    private fun initView() {
        binding.viewHeader.txtTitle.text=requireActivity().getString(R.string.leaderboard)
        binding.viewHeader.imgProfile.isVisible=false
        dataList.clear()



        adapter = AwardAdapter(requireActivity(),preferenceManager, dataList) { pos, type ->
            onAdapterClick(
                pos,
                type
            )
        }
        binding.viewBody.rvList.layoutManager = LinearLayoutManager(requireActivity())
        binding.viewBody.rvList.adapter = adapter
        binding.viewBody.tvMessage.isVisible = false

        binding.rgType.setOnCheckedChangeListener { group, checkedId ->

            when (checkedId) {

                R.id.rbWinning -> {
                    binding.rbWinning.setTextColor(ContextCompat.getColor(requireActivity(), R.color.app_color))
                    binding.rbMostPlayed.setTextColor(ContextCompat.getColor(requireActivity(), R.color.textPlaceHolder))
                    binding.tvWinning.text=getString(R.string.winnings)
                    type = "winning"

                }
                R.id.rbMostPlayed -> {
                    binding.rbMostPlayed.setTextColor(ContextCompat.getColor(requireActivity(), R.color.app_color))
                    binding.rbWinning.setTextColor(ContextCompat.getColor(requireActivity(), R.color.textPlaceHolder))
                    binding.tvWinning.text=getString(R.string.overs_played)
                    type = "most_played"

                }

            }
            dataList.clear()
            time_period="day"
            if (!binding.rbDaily.isChecked){ binding.rbDaily.isChecked = true } else {
                callListAPI()
            }

        }
        binding.rgTime.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rbDaily -> {
                    binding.rbDaily.setTextColor(ContextCompat.getColor(requireActivity(), R.color.app_color))
                    binding.rbYear.setTextColor(ContextCompat.getColor(requireActivity(), R.color.textPlaceHolder))
                    binding.rbMonthly.setTextColor(ContextCompat.getColor(requireActivity(), R.color.textPlaceHolder))
                    time_period = "day"


                }

                R.id.rbMonthly -> {
                    binding.rbMonthly.setTextColor(ContextCompat.getColor(requireActivity(), R.color.app_color))
                    binding.rbDaily.setTextColor(ContextCompat.getColor(requireActivity(), R.color.textPlaceHolder))
                    binding.rbYear.setTextColor(ContextCompat.getColor(requireActivity(), R.color.textPlaceHolder))

                    time_period = "month"


                }
                R.id.rb_year -> {
                    binding.rbYear.setTextColor(ContextCompat.getColor(requireActivity(), R.color.app_color))
                    binding.rbDaily.setTextColor(ContextCompat.getColor(requireActivity(), R.color.textPlaceHolder))
                    binding.rbMonthly.setTextColor(ContextCompat.getColor(requireActivity(), R.color.textPlaceHolder))

                    time_period = "year"


                }
            }
            dataList.clear()
            callListAPI()

        }
    }

    private fun callListAPI() {
        binding.viewBody.tvMessage.isVisible = true
        binding.viewBody.tvMessage.text = getString(R.string.loading)
        if (utilsManager.isNetworkConnected()) {

            val  json= JsonObject()
             json.addProperty("type", type)
             json.addProperty("period", time_period)


            viewModal.getLeaderboardList(
                requireActivity(), preferenceManager.getAuthToken(), json).observe(viewLifecycleOwner, androidx.lifecycle.Observer { res ->
                dataList.clear()
                if (res.status) {

                    dataList.addAll(res.data.leaderboard)


                }


                updateUI(res.message)
            })
        }else{
            updateUI(getString(R.string.no_internet_connection_please_try_again))
        }
    }
    private fun updateUI(message:String) {
        adapter.notifyDataSetChanged()
        if (dataList.isEmpty()){
            binding.viewBody.tvMessage.isVisible=true
            binding.viewBody.tvMessage.text=message
        }else{
            binding.viewBody.tvMessage.isVisible=false
        }
    }
    private fun setUserUIData() {
        val userDetails = preferenceManager.getLoginData()!!
        binding.viewHeader.txtTitle.text = "Hi,${userDetails?.full_name.toString()}"
        loadProfileImage(userDetails?.avatar_full_path.toString(), binding.viewHeader.imgProfile)
    }
    private fun onAdapterClick(pos: Int, type: String) {

    }
    override fun onDestroyView() {
        super.onDestroyView()

    }
}