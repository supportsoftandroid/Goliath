package com.fantasy.goliath.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.fantasy.goliath.databinding.FragmentAddOverBinding


import com.fantasy.goliath.model.CommonDataItem
import com.fantasy.goliath.model.LoginResponse
import com.fantasy.goliath.ui.adapter.SelectedOverAdapter
import com.fantasy.goliath.utility.PreferenceManager
import com.fantasy.goliath.utility.StaticData
import com.fantasy.goliath.utility.StaticData.Companion.showToast
import com.fantasy.goliath.utility.UtilsManager
import com.fantasy.goliath.viewmodal.AddOverViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog

class AddOverFragment : Fragment() {
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
    lateinit var preferences: PreferenceManager
    lateinit var utilsManager: UtilsManager
    lateinit var adapter: SelectedOverAdapter
    var dataList = mutableListOf<CommonDataItem>()
    var count=1
    var totalOver=50
    var lastAddedOver="Over 15"

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

        binding.btnBack.setOnClickListener() {

          requireActivity().onBackPressed()

        }

        binding.imgMinus.setOnClickListener() {
            if (count>1){
                count=count-1
                binding.tvCount.setText("Over "+count.toString())
            }

        }
        binding.imgPlus.setOnClickListener() {
            if (count<totalOver){
                count=count+1
                binding.tvCount.setText("Over "+count.toString())
                binding.tvSelectedOverLabel.visibility=View.VISIBLE
                binding.tvClear.visibility=View.VISIBLE

            }
        }
        binding.tvAdd.setOnClickListener() {
             val newOverValue=binding.tvCount.text.toString()
            checkNotDuplicateOverValue(newOverValue)

        }
        binding.tvClear.setOnClickListener() {
            dataList.clear()
            adapter.notifyDataSetChanged()
            binding.tvSelectedOverLabel.visibility=View.GONE
            binding.tvClear.visibility=View.GONE
        }
        binding.btnConform.setOnClickListener() {
            utilsManager.showWalletErrorDialog(requireActivity(),{type,dialog->onWalletCheck(type,dialog)})

        }

    }

    private fun onWalletCheck(type: String, dialog: BottomSheetDialog) {
        dialog.dismiss()
        StaticData.backStackAddFragment(
            requireActivity(),
            AddQuestionsFragment.newInstance("add")
        )
    }

    private fun checkNotDuplicateOverValue(newOverValue: String):Boolean {
       var isNotDuplicate=true
        for ( i in dataList ){
            if (i.title==newOverValue){
                isNotDuplicate=false
                break

            }
        }
      if (isNotDuplicate)  {
          dataList.add(CommonDataItem(newOverValue,"",false))
        adapter.notifyDataSetChanged()

    }else{
        showToast(requireActivity(),"Duplicate over not allowed")
    }
        return isNotDuplicate

    }

    private fun initView() {
        dataList.clear()

        dataList.add(CommonDataItem("Over 1", "",false))
        dataList.add(CommonDataItem("Over 2", "",false))
        dataList.add(CommonDataItem("Over 5", "",false))
        dataList.add(CommonDataItem("Over 10", "",false))
        dataList.add(CommonDataItem("Over 15", "",false))


        adapter = SelectedOverAdapter(requireActivity(), dataList, { pos, type -> onAdapterClick(pos, type) })
        binding.rvList.layoutManager = GridLayoutManager(requireActivity(),3)
        binding.rvList.adapter = adapter



    }

    private fun onAdapterClick(pos: Int, type: String) {

    }
    override fun onDestroyView() {
        super.onDestroyView()

    }
}