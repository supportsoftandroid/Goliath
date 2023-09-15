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
import com.fantasy.goliath.databinding.FragmentHomeBinding
import com.fantasy.goliath.model.LoginResponse
import com.fantasy.goliath.model.MatchDataItem
import com.fantasy.goliath.ui.activities.MainActivity
import com.fantasy.goliath.ui.adapter.MatchItemAdapter
import com.fantasy.goliath.ui.fragments.AddOverFragment
import com.fantasy.goliath.utility.PreferenceManager
import com.fantasy.goliath.utility.StaticData
import com.fantasy.goliath.utility.UtilsManager
import com.fantasy.goliath.viewmodal.HomeViewModel

class HomeFragment : Fragment() {


    private val viewModal by lazy { ViewModelProvider(this)[HomeViewModel::class.java] }

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentHomeBinding.inflate(layoutInflater)
    }
    lateinit var loginResponse: LoginResponse
    lateinit var preferences: PreferenceManager
    lateinit var utilsManager: UtilsManager
    lateinit var adapter: MatchItemAdapter
    var dataList = mutableListOf<MatchDataItem>()
    var  type = "upcoming"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root: View = binding.root
        binding.let {
            preferences=PreferenceManager(requireActivity())
            utilsManager=UtilsManager(requireActivity())
            initView()
            clickListener()
        }


        return root
    }
    private fun clickListener() {

        binding.viewHeader.imgProfile.setOnClickListener() {

             MainActivity.navProfileTab()

        }

    }

    private fun initView() {
        dataList.clear()

        dataList.add(MatchDataItem("GT","CSK","T20"))
        dataList.add(MatchDataItem("PBKS","KKR","One day"))
        dataList.add(MatchDataItem("DC","LSG","T20"))
        dataList.add(MatchDataItem("SRH","RR","One day"))
        dataList.add(MatchDataItem("MI","RCB","T20"))
        adapter = MatchItemAdapter(requireActivity(), dataList, { pos, type -> onAdapterClick(pos, type) })
        binding.viewBody.rvList.layoutManager = LinearLayoutManager(requireActivity())
        binding.viewBody.rvList.adapter = adapter
        binding.rgStatus.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rbUpcoming -> {
                    binding.rbUpcoming.setTextColor(ContextCompat.getColor(requireActivity(), R.color.app_color))
                    binding.rbLive.setTextColor(ContextCompat.getColor(requireActivity(), R.color.textPlaceHolder))
                    type = "upcoming"
                    adapter.updateMatchType(type)

                }
                R.id.rbLive -> {
                    binding.rbLive.setTextColor(ContextCompat.getColor(requireActivity(), R.color.app_color))
                    binding.rbUpcoming.setTextColor(ContextCompat.getColor(requireActivity(), R.color.textPlaceHolder))
                    type = "live"
                    adapter.updateMatchType(type)

                }
            }

        }


    }

    private fun onAdapterClick(pos: Int, type: String) {
        MainActivity.hideNavigationTab()
        StaticData.backStackAddFragment(
            requireActivity(),
            AddOverFragment.newInstance("add")
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }
}