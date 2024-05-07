package com.fantasy.goliath.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider

import androidx.recyclerview.widget.LinearLayoutManager
import com.fantasy.goliath.databinding.FragmentGuideGameBinding
import com.fantasy.goliath.model.HowToPlayItem

import com.fantasy.goliath.ui.adapter.GameGuideAdapter
import com.fantasy.goliath.ui.base.BaseFragment
import com.fantasy.goliath.viewmodal.GameGuideViewModel



class GameGuideFragment : BaseFragment() {
    private val binding by lazy { FragmentGuideGameBinding.inflate(layoutInflater) }
    private val viewModal by lazy { ViewModelProvider(this)[GameGuideViewModel::class.java] }
    lateinit var adapter: GameGuideAdapter
    var dataList = mutableListOf<HowToPlayItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        initView()
        clickListener()
        callListAPI()

        return binding.root

    }

    fun clickListener() {
        binding.imgBack.setOnClickListener() {
            onBackPressed()
        }
        binding.btnNext.setOnClickListener() {
            gotoMainActivity()
        }
    }


    private fun initView() {
        dataList.clear()

        dataList.add(HowToPlayItem("","Signup www.goliath101.com" ))
        dataList.add(HowToPlayItem("","Make your match Selection" ))
        var strText = "Select The " + getBoldText("OVER(S)") + "  to play"
        dataList.add(HowToPlayItem( "",strText))
        dataList.add(HowToPlayItem("","Select one or multiple OVERS" ))
        dataList.add(HowToPlayItem("","Make your predictions" ))
        dataList.add(HowToPlayItem("","Load your Wallet" ))

        strText = "Entry fee" + getBoldText("₹400") + "  per OVER"

        dataList.add(HowToPlayItem( "",strText))
        dataList.add(HowToPlayItem("","Check the APP"))
        strText = "See instant results" + getBoldText("WIN/LOSE")

        dataList.add(HowToPlayItem( "",strText))
        dataList.add(HowToPlayItem("","Check your winning status." ))
        dataList.add(HowToPlayItem("","Check your wallet" ))
        strText = "Predictions for next " + getBoldText("OVER")
        dataList.add(HowToPlayItem( "",strText))

        adapter = GameGuideAdapter(
            requireActivity(),
            dataList,
            { pos, type -> onAdapterClick(pos, type) })
        binding.rvList.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvList.adapter = adapter
    }

    private fun getBoldText(text: String): String {
        return "<font color=#438DCA><b>$text</b></font>"

    }

    private fun onAdapterClick(pos: Int, type: String) {

    }

    private fun callListAPI() {

        if (utilsManager.isNetworkConnected()) {


            viewModal.getGuideList(
                requireActivity(), preferenceManager.getAuthToken()
            ).observe(viewLifecycleOwner, androidx.lifecycle.Observer { res ->

                if (res.status) {
                    dataList.clear()
                    dataList.addAll(res.data.how_to_paly)
                }
                adapter.notifyDataSetChanged()
            })
        }


    }
}
