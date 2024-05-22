package com.fantasy.goliath.ui.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fantasy.goliath.R

import com.fantasy.goliath.databinding.FragmentMyPredictionBinding

import com.fantasy.goliath.model.MatchItem
import com.fantasy.goliath.ui.adapter.MatchItemAdapter
import com.fantasy.goliath.ui.base.BaseFragment
import com.fantasy.goliath.viewmodal.MyPredictionViewModel
import com.google.gson.JsonObject

class MatchHistoryFragment : BaseFragment() {

    companion object {
        fun newInstance(from: String, title: String): MatchHistoryFragment {
            val args = Bundle()
            args.putString("from", from)
            args.putString("title", title)
            val fragment = MatchHistoryFragment()
            fragment.arguments = args
            return fragment
        }
    }
    private val viewModal by lazy { ViewModelProvider(this)[MyPredictionViewModel::class.java] }

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentMyPredictionBinding.inflate(layoutInflater)
    }


    lateinit var adapter: MatchItemAdapter
    var dataList = arrayListOf<MatchItem>()

    var isLoading=false
    var currentPage=1
    var selectedPos=-1
    var totalPage=""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding.let {
            dataList.clear()
            currentPage=1
            selectedPos=-1
            initView()
            clickListener()
            callListAPI()
        }


        return binding.root
    }
    private fun clickListener() {

        binding.viewHeader.setClickListener(this)
    }

    private fun initView() {
        binding.viewMainHeader.toolbarTab.isVisible=false
        binding.viewHeader.isVisible=true
        binding.viewHeader.setTitle( requireActivity().getString(R.string.my_predictions))
        dataList.clear()


        adapter = MatchItemAdapter(
            requireActivity(),
            dataList,
            { pos, type -> onAdapterClick(pos, type) })
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




    }

    private fun onAdapterClick(pos: Int, type: String) {
        addFragmentToBackStack ( MatchOverResultStatusFragment.newInstance("history",dataList[pos]))
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

    private fun callListAPI( ) {

            if (utilsManager.isNetworkConnected()) {
                isLoading=true
                val  json= JsonObject()
               // json.addProperty("status", matchStatus)


                viewModal.getMyPredictionList(
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
            }


    }

    private fun updateUI(message:String) {
        adapter.updateMatchType()
        if (dataList.isEmpty()){
            binding.viewBody.tvMessage.isVisible=true
            binding.viewBody.tvMessage.text=message
        }else{
            binding.viewBody.tvMessage.isVisible=false
        }
    }
}