package com.fantasy.goliath.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fantasy.goliath.R
import com.fantasy.goliath.databinding.FragmentTransactionHistoryBinding
import com.fantasy.goliath.model.CommonDataItem
import com.fantasy.goliath.model.LoginResponse
import com.fantasy.goliath.ui.adapter.TransactionAdapter
import com.fantasy.goliath.ui.base.BaseFragment
import com.fantasy.goliath.utility.PreferenceManager
import com.fantasy.goliath.utility.UtilsManager
import com.fantasy.goliath.viewmodal.HomeViewModel

class TransactionHistoryFragment : BaseFragment() {

    companion object {
        fun newInstance(from: String ): TransactionHistoryFragment {
            val args = Bundle()
            args.putString("from", from)
            val fragment = TransactionHistoryFragment()
            fragment.arguments = args
            return fragment
        }
    }
    private val viewModal by lazy { ViewModelProvider(this)[HomeViewModel::class.java] }

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentTransactionHistoryBinding.inflate(layoutInflater)
    }
    lateinit var loginResponse: LoginResponse

    lateinit var adapter: TransactionAdapter
    var dataList = mutableListOf<CommonDataItem>()
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
        binding.viewHeader.getToolBarView().imgMenu1.isVisible=false
        binding.viewHeader.getToolBarView().imgMenu2.isVisible=false
        binding.viewHeader.setClickListener(this)

    }

    private fun initView() {
        binding.viewHeader.setTitle( requireActivity().getString(R.string.transaction_history))
        dataList.clear()
        binding.viewBody.tvMessage.isVisible = false
        dataList.add(CommonDataItem("Total Fee Pay","CSK",false))
        dataList.add(CommonDataItem("Total Fee Pay","CSK",false))
        dataList.add(CommonDataItem("Total Fee Pay","CSK",false))
        dataList.add(CommonDataItem("Total Fee Pay","CSK",false))
        dataList.add(CommonDataItem("Total Fee Pay","CSK",false))
        dataList.add(CommonDataItem("Total Fee Pay","CSK",false))
        dataList.add(CommonDataItem("Total Fee Pay","CSK",false))
        dataList.add(CommonDataItem("Total Fee Pay","CSK",false))
        dataList.add(CommonDataItem("Total Fee Pay","CSK",false))
        dataList.add(CommonDataItem("Total Fee Pay","CSK",false))
        dataList.add(CommonDataItem("Total Fee Pay","CSK",false))
        dataList.add(CommonDataItem("Total Fee Pay","CSK",false))
        dataList.add(CommonDataItem("Total Fee Pay","CSK",false))

        adapter = TransactionAdapter(requireActivity(), dataList, { pos, type -> onAdapterClick(pos, type) })
        binding.viewBody.rvList.layoutManager = LinearLayoutManager(requireActivity())
        binding.viewBody.rvList.adapter = adapter
        type = "credit"
        adapter.updateMatchType(type)
        binding.rgStatus.setOnCheckedChangeListener { group, checkedId ->
            //green
            updateGreenTab(binding.rbDeposit)
            updateGreenTab(binding.rbWinning)
            //red
            updateRedTab(binding.rbWithdraw)
            updateRedTab(binding.rbTotalFeePaid)
            when (checkedId) {
                R.id.rbDeposit,R.id.rbWinning, -> {
                    type = "credit"
                    adapter.updateMatchType(type)

                }
                R.id.rbWithdraw, R.id.rbTotalFeePaid, -> {

                    type = "debit"
                    adapter.updateMatchType(type)

                }
            }

        }


    }

    fun updateGreenTab(radioButton: RadioButton) {
        if (radioButton.isChecked) {
            radioButton.setTextColor(ContextCompat.getColor(requireActivity(), R.color.colorWhite))
        }else{
            radioButton.setTextColor(ContextCompat.getColor(requireActivity(), R.color.color2EB100))
        }
    }
    fun updateRedTab(radioButton: RadioButton) {
        if (radioButton.isChecked) {
            radioButton.setTextColor(ContextCompat.getColor(requireActivity(), R.color.colorWhite))
        }else{
            radioButton.setTextColor(ContextCompat.getColor(requireActivity(), R.color.colorRed))
        }
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