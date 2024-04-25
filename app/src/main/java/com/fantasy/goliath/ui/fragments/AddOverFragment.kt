package com.fantasy.goliath.ui.fragments

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.fantasy.goliath.R
import com.fantasy.goliath.databinding.FragmentAddOverBinding
import com.fantasy.goliath.databinding.ListOverStatusItemBinding


import com.fantasy.goliath.model.CommonDataItem
import com.fantasy.goliath.model.LoginResponse
import com.fantasy.goliath.ui.adapter.SelectedOverAdapter
import com.fantasy.goliath.ui.base.BaseFragment
import com.fantasy.goliath.utility.showToast
import com.fantasy.goliath.utility.showWalletErrorDialog


import com.fantasy.goliath.viewmodal.AddOverViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.health.kharma.ui.adapters.MyAdapter

class AddOverFragment : BaseFragment() {
    companion object {
        fun newInstance(from: String ): AddOverFragment {
            val args = Bundle()
            args.putString("from", from)
            val fragment = AddOverFragment()
            fragment.arguments = args
            return fragment
        }
    }
    private val viewModal by lazy { ViewModelProvider(this)[AddOverViewModel::class.java] }
    private val binding by lazy(LazyThreadSafetyMode.NONE) { FragmentAddOverBinding.inflate(layoutInflater) }
    lateinit var loginResponse: LoginResponse

    lateinit var adapter: SelectedOverAdapter
    var dataList = mutableListOf<CommonDataItem>()
    var overStatusList = mutableListOf<CommonDataItem>()
    var count=1
    var totalOver=50
    var lastAddedOver="Over 15"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        val root: View = binding.root
        binding.let {
            overStatusList.clear()
            initView()
            clickListener()
        }

        return root
    }
    private fun clickListener() {

        binding.clvHeader.setClickListener(this)





        binding.btnConform.setOnClickListener() {
            showWalletErrorDialog(requireActivity(),{type,dialog->onWalletCheck(type,dialog)})

        }

    }

    private fun onWalletCheck(type: String, dialog: BottomSheetDialog) {
        dialog.dismiss()
        addFragmentToBackStack(
            AddQuestionsFragment.newInstance("add")
        )
    }



    private fun initView() {
        dataList.clear()

        dataList.add(CommonDataItem("1", "completed",false))
        dataList.add(CommonDataItem("2", "completed",false))
        dataList.add(CommonDataItem("3", "completed",false))
        dataList.add(CommonDataItem("4", "completed",false))
        dataList.add(CommonDataItem("5", "completed",false))
        dataList.add(CommonDataItem("6", "completed",false))
        dataList.add(CommonDataItem("7", "ongoing",false))
        dataList.add(CommonDataItem("8", "predicted",false))
        dataList.add(CommonDataItem("9", "available",false))
        dataList.add(CommonDataItem("10", "predicted",false))
        dataList.add(CommonDataItem("11", "available",false))
        dataList.add(CommonDataItem("12", "available",true))
        dataList.add(CommonDataItem("13", "upcoming",false))
        dataList.add(CommonDataItem("14", "upcoming",false))
        dataList.add(CommonDataItem("15", "upcoming",false))
        dataList.add(CommonDataItem("16", "upcoming",false))
        dataList.add(CommonDataItem("17", "upcoming",false))
        dataList.add(CommonDataItem("18", "upcoming",false))
        dataList.add(CommonDataItem("19", "upcoming",false))
        dataList.add(CommonDataItem("20", "upcoming",false))


        adapter = SelectedOverAdapter(requireActivity(), dataList, { pos, type -> onAdapterClick(pos, type) })
        binding.rvList.layoutManager = GridLayoutManager(requireActivity(),6)
        binding.rvList.adapter = adapter
        overStatusList.clear()

        overStatusList.add(CommonDataItem("Overs Completed", "completed",false))
        overStatusList.add(CommonDataItem("Ongoing Over", "ongoing",false))
        overStatusList.add(CommonDataItem("Predicted Overs", "predicted",false))
        overStatusList.add(CommonDataItem("Available Overs", "available",false))
        overStatusList.add(CommonDataItem("Selected Over", "selected",false))
        overStatusList.add(CommonDataItem("Upcoming Overs", "upcoming",false))

       val adapterStatus = MyAdapter(
            R.layout.list_over_status_item,
           overStatusList
        ) { view, data, pos ->

            ListOverStatusItemBinding.bind(view).apply {
                tvTitle.text=data.title
                when(data.type.lowercase()){
                    "completed"->{

                       // tvTitle.setTextColor(ContextCompat.getColor(rea, R.color.colorRed))
                        imgProduct.backgroundTintList= ColorStateList.valueOf(ContextCompat.getColor(requireActivity(), R.color.colorRedLight))
                    }
                    "predicted"->{

                       // tvTitle.setTextColor(ContextCompat.getColor(rea, R.color.colorRed))
                        imgProduct.backgroundTintList= ColorStateList.valueOf(ContextCompat.getColor(requireActivity(), R.color.color2EB100))
                    }
                    "ongoing"->{

                       // tvTitle.setTextColor(ContextCompat.getColor(rea, R.color.colorRed))
                        imgProduct.backgroundTintList= ColorStateList.valueOf(ContextCompat.getColor(requireActivity(), R.color.colorRed))
                    }
                    "selected"->{
                        imgProduct.backgroundTintList= ColorStateList.valueOf(ContextCompat.getColor(requireActivity(), R.color.app_color))
                    }
                    "available"->{
                        imgProduct.setBackgroundResource(R.drawable.border_layout_blue_radius_5)
                    }
                    "upcoming"->{
                        imgProduct.setBackgroundResource(R.drawable.border_layout_blue_light_radius_5)
                    }


                }




            }
        }

        binding.rvOverStatusList.layoutManager =  GridLayoutManager(requireActivity(),3)
        binding.rvOverStatusList.adapter = adapterStatus
    }

    private fun onAdapterClick(pos: Int, type: String) {

    }
    override fun onDestroyView() {
        super.onDestroyView()

    }
}