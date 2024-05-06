package com.fantasy.goliath.ui.fragments.nav

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fantasy.goliath.R
import com.fantasy.goliath.databinding.FragmentMatchHistoryBinding
import com.fantasy.goliath.model.MatchItem
import com.fantasy.goliath.ui.activities.MainActivity
import com.fantasy.goliath.ui.adapter.MatchItemAdapter
import com.fantasy.goliath.ui.base.BaseFragment
import com.fantasy.goliath.ui.fragments.NotificationsFragment
import com.fantasy.goliath.ui.fragments.WalletDetailsFragment
import com.fantasy.goliath.utility.Constants
import com.fantasy.goliath.viewmodal.HomeViewModel
import com.google.gson.JsonObject

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
    var dataList = mutableListOf<MatchItem>()
    var isLoading=false
    var currentPage=1
    var selectedPos=-1
    var totalPage=""
    var  matchStatus = "Live"


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding.let {

            initView()
            clickListener()
            callListAPI()

        }


        return binding.root
    }
    private fun clickListener() {

        binding.viewHeader.setClickListener(this)
        binding.viewHeader.getToolBarView().imgMenu2.setOnClickListener() {
            onNotificationsIconClick()
        }
        binding.viewHeader.getToolBarView().imgMenu1.setOnClickListener() {
            onWalletIconClick()

        }
    }

    private fun initView() {
        binding.viewMainHeader.toolbarTab.isVisible=true
        binding.viewMainHeader.imgProfile.isVisible=false
        binding.viewHeader.isVisible=false

        binding.viewBody.tvMessage.isVisible=true
        setUserUIData()
        //binding.viewHeader.setTitle( requireActivity().getString(R.string.matches_history))
        dataList.clear()


        adapter = MatchItemAdapter(requireActivity(), dataList, { pos, type -> onAdapterClick(pos, type) })
        val layoutManager= LinearLayoutManager(requireActivity())
        binding.viewBody.rvList.layoutManager =layoutManager
        binding.viewBody.rvList.adapter = adapter

        binding.viewBody.rvList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(
                recyclerView: RecyclerView,
                dx: Int, dy: Int
            ) {
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
        binding.viewMainHeader.txtTitle.text =requireActivity().getString(R.string.my_predictions)
        loadProfileImage(userDetails?.avatar_full_path.toString(), binding.viewMainHeader.imgProfile)
    }
    private fun onAdapterClick(pos: Int, type: String) {
        //MainActivity.hideNavigationTab()
       /* UiUtils.backStackAddFragment(
            requireActivity(),
            AddOverFragment.newInstance("add")
        )*/
    }

    private fun callListAPI( ) {

        if (utilsManager.isNetworkConnected()) {
            isLoading=true
            val  json= JsonObject()
            json.addProperty("status", matchStatus)


            viewModal.getMatchesList(
                requireActivity(), preferenceManager.getAuthToken(), currentPage, json).observe(viewLifecycleOwner, androidx.lifecycle.Observer { res ->
                if (res.status) {
                    totalPage=res.data.matchlist.next_page_url
                    dataList.addAll(res.data.matchlist.data)
                    currentPage++

                }else{
                    totalPage=""
                }
                isLoading=false
                updateUI(res.message)
            })
        }else{
            binding.viewBody.tvMessage.isVisible=true
            binding.viewBody.tvMessage.text=
                getString(R.string.no_internet_connection_please_try_again)
        }


    }

    private fun updateUI(message: String) {
        adapter.updateMatchType(matchStatus)
        if (dataList.isEmpty()){
            binding.viewBody.tvMessage.isVisible=true
            binding.viewBody.tvMessage.text=message
        }else{
            binding.viewBody.tvMessage.isVisible=false
        }
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