package com.fantasy.goliath.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.LinearLayoutManager
import com.fantasy.goliath.databinding.FragmentGuideGameBinding
import com.fantasy.goliath.model.CommonDataItem
import com.fantasy.goliath.ui.adapter.GameGuideAdapter
import com.fantasy.goliath.ui.base.BaseFragment


class GameGuideFragment : BaseFragment() {


    private val binding by lazy { FragmentGuideGameBinding.inflate(layoutInflater) }
    lateinit var adapter: GameGuideAdapter
    var dataList = mutableListOf<CommonDataItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

            initView()
            clickListener()

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

        dataList.add(CommonDataItem("Signup www.goliath101.com", "", false))
        dataList.add(CommonDataItem("Make your match Selection", "", false))
        var strText = "Select The " + getBoldText("OVER(S)") + "  to play"
        dataList.add(CommonDataItem(strText, "", false))
        dataList.add(CommonDataItem("Select one or multiple OVERS", "", false))
        dataList.add(CommonDataItem("Make your predictions", "", false))
        dataList.add(CommonDataItem("Load your Wallet", "", false))

        strText = "Entry fee" + getBoldText("₹501") + "  per OVER"

        dataList.add(CommonDataItem(strText, "", false))
        dataList.add(CommonDataItem("Check the APP", "", false))
        strText = "See instant results" + getBoldText("WIN/LOSE")

        dataList.add(CommonDataItem(strText, "", false))
        dataList.add(CommonDataItem("Check your winning status.", "", false))
        dataList.add(CommonDataItem("Check your wallet", "", false))
        strText = "Predictions for next " + getBoldText("OVER")
        dataList.add(CommonDataItem(strText, "", false))

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


}
