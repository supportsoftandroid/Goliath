package com.fantasy.goliath.ui.fragments.nav

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fantasy.goliath.R
import com.fantasy.goliath.databinding.FragmentHomeBinding
import com.fantasy.goliath.model.LoginResponse
import com.fantasy.goliath.model.MatchItem
import com.fantasy.goliath.ui.activities.MainActivity
import com.fantasy.goliath.ui.adapter.MatchItemAdapter
import com.fantasy.goliath.ui.base.BaseFragment
import com.fantasy.goliath.ui.fragments.AddOverFragment
import com.fantasy.goliath.ui.fragments.NotificationsFragment
import com.fantasy.goliath.ui.fragments.WalletDetailsFragment
import com.fantasy.goliath.utility.Constants

import com.fantasy.goliath.viewmodal.HomeViewModel
import com.google.gson.JsonObject

class HomeFragment : BaseFragment() {


    private val viewModal by lazy { ViewModelProvider(this)[HomeViewModel::class.java] }

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentHomeBinding.inflate(layoutInflater)
    }
    lateinit var loginResponse: LoginResponse

    lateinit var adapter: MatchItemAdapter
    var dataList = mutableListOf<MatchItem>()
    var matchStatus = "Live"
    var isLoading=false
    var currentPage=1
    var selectedPos=-1
    var totalPage=""
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding.let {
            initView()
            clickListener()
            callListAPI()
        }

        return binding.root
    }

      fun clickListener() {

        binding.viewHeader.imgProfile.setOnClickListener() {
            MainActivity.navProfileTab()
        }
        binding.viewHeader.imgMenu2.setOnClickListener() {
            addFragmentToBackStack(NotificationsFragment())

        }
        binding.viewHeader.imgMenu1.setOnClickListener() {
            addFragmentToBackStack(WalletDetailsFragment())

        }

    }

    private fun initView() {
        dataList.clear()
        binding.viewBody.tvMessage.isVisible=true
        setUserUIData()
        binding.rgStatus.setOnCheckedChangeListener { group, checkedId ->
            dataList.clear()
            currentPage=1
            binding.viewBody.rvList.getRecycledViewPool().clear()
            adapter.notifyDataSetChanged()
            when (checkedId) {
                R.id.rbUpcoming -> {
                    binding.rbUpcoming.setTextColor(
                        ContextCompat.getColor(
                            requireActivity(),
                            R.color.app_color
                        )
                    )
                    binding.rbLive.setTextColor(
                        ContextCompat.getColor(
                            requireActivity(),
                            R.color.textPlaceHolder
                        )
                    )
                    matchStatus = "Scheduled"

                }

                R.id.rbLive -> {
                    binding.rbLive.setTextColor(
                        ContextCompat.getColor(
                            requireActivity(),
                            R.color.app_color
                        )
                    )
                    binding.rbUpcoming.setTextColor(
                        ContextCompat.getColor(
                            requireActivity(),
                            R.color.textPlaceHolder
                        )
                    )
                    matchStatus = "Live"



                }

            }
            binding.viewBody.tvMessage.isVisible=true
            binding.viewBody.tvMessage.text=requireActivity().getString(R.string.loading)

            callListAPI()
        }

        adapter = MatchItemAdapter(
            requireActivity(),
            dataList,
            { pos, type -> onAdapterClick(pos, type) })
        val layoutManager= LinearLayoutManager(requireActivity())
        binding.viewBody.rvList.layoutManager =layoutManager
        binding.viewBody.rvList.adapter = adapter

        binding.viewBody.rvList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount: Int = layoutManager.getChildCount()
                val totalItemCount: Int = layoutManager.getItemCount()
                val firstVisibleItemPosition: Int = layoutManager.findFirstVisibleItemPosition()
                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                    && firstVisibleItemPosition >= 0
                ) {
                    if (!isLoading &&!TextUtils.isEmpty(totalPage)) {
                        callListAPI()
                    }
                }

            }
        })
        setFragmentResultListener(Constants.EDIT_PROFILE_OTHER_REQUEST_KEY) { key, bundle ->
            // Any type can be passed via to the bundle
            val from = bundle.getString("from").toString()
            if (from.equals("edit_profile")) {
                setUserUIData()
            }
            // Do something with the result...
        }
    }

    private fun setUserUIData() {
        val userDetails = preferenceManager.getLoginData()!!
        binding.viewHeader.txtTitle.text = "Hi,${userDetails?.full_name.toString()}"
        loadProfileImage(userDetails?.avatar_full_path.toString(), binding.viewHeader.imgProfile)
    }

    private fun onAdapterClick(pos: Int, type: String) {
        MainActivity.hideNavigationTab()
        addFragmentToBackStack(

            AddOverFragment.newInstance("add",dataList[pos])
        )
    }
    private fun callListAPI( ) {

            if (utilsManager.isNetworkConnected()) {
                isLoading=true
                val  json= JsonObject()
                json.addProperty("status", matchStatus)
                viewModal.getMatchesList(
                    requireActivity(), preferenceManager.getAuthToken(), currentPage, json).observe(viewLifecycleOwner, androidx.lifecycle.Observer { res ->
                    totalPage=""
                        if (res.status) {
                            if(res.data.matchlist!=null) {
                                totalPage = res.data.matchlist.next_page_url
                                dataList.addAll(res.data.matchlist.data)
                                currentPage++
                            }

                    }else{
                        totalPage=""
                    }
                    isLoading=false
                    updateUI(res.message)
                })
            }


    }

    private fun updateUI(message:String) {
        adapter.updateMatchType(matchStatus)
        if (dataList.isEmpty()){
            binding.viewBody.tvMessage.isVisible=true
            binding.viewBody.tvMessage.text=message
        }else{
            binding.viewBody.tvMessage.isVisible=false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }
}