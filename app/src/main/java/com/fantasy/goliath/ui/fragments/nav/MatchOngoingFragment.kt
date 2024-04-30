package com.fantasy.goliath.ui.fragments.nav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fantasy.goliath.R
import com.fantasy.goliath.databinding.FragmentMatchHistoryBinding
import com.fantasy.goliath.model.LoginData
import com.fantasy.goliath.model.LoginResponse
import com.fantasy.goliath.model.MatchDataItem
import com.fantasy.goliath.model.UserDetails
import com.fantasy.goliath.ui.adapter.MatchItemAdapter
import com.fantasy.goliath.ui.base.BaseFragment
import com.fantasy.goliath.utility.PreferenceManager
import com.fantasy.goliath.utility.UtilsManager
import com.fantasy.goliath.viewmodal.HomeViewModel

class MatchOngoingFragment : BaseFragment() {

    companion object {
        fun newInstance(from: String ): MatchOngoingFragment {
            val args = Bundle()
            args.putString("from", from)
            val fragment = MatchOngoingFragment()
            fragment.arguments = args
            return fragment
        }
    }
    private val viewModal by lazy { ViewModelProvider(this)[HomeViewModel::class.java] }

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentMatchHistoryBinding.inflate(layoutInflater)
    }


    lateinit var adapter: MatchItemAdapter
    var dataList = mutableListOf<MatchDataItem>()
    var  type = "upcoming"


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding.let {

            initView()
            clickListener()
        }


        return binding.root
    }
    private fun clickListener() {

        binding.viewHeader.setClickListener(this)
    }

    private fun initView() {
        binding.viewMainHeader.toolbarTab.isVisible=true
        binding.viewHeader.isVisible=false
        //binding.viewHeader.setTitle( requireActivity().getString(R.string.matches_history))
        dataList.clear()

        dataList.add(MatchDataItem("GT","CSK","T20"))
        dataList.add(MatchDataItem("PBKS","KKR","One day"))
        dataList.add(MatchDataItem("DC","LSG","T20"))
        adapter = MatchItemAdapter(requireActivity(), dataList, { pos, type -> onAdapterClick(pos, type) })
        binding.viewBody.rvList.layoutManager = LinearLayoutManager(requireActivity())
        binding.viewBody.rvList.adapter = adapter
        type = "completed"
        adapter.updateMatchType(type)



    }

    private fun onAdapterClick(pos: Int, type: String) {
        //MainActivity.hideNavigationTab()
       /* UiUtils.backStackAddFragment(
            requireActivity(),
            AddOverFragment.newInstance("add")
        )*/
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onNotificationsIconClick() {
        super.onNotificationsIconClick()
    }

    override fun onWalletIconClick() {
        super.onWalletIconClick()
    }
}