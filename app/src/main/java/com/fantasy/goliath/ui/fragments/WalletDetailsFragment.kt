package com.fantasy.goliath.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible

import androidx.lifecycle.ViewModelProvider
import com.fantasy.goliath.R
import com.fantasy.goliath.databinding.FragmentWalletDetailsBinding
import com.fantasy.goliath.model.LoginResponse
import com.fantasy.goliath.ui.base.BaseFragment
import com.fantasy.goliath.utility.showAddAmountDialog

import com.fantasy.goliath.viewmodal.ProfileViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog

class WalletDetailsFragment : BaseFragment() {
    companion object {
        fun newInstance(from: String): WalletDetailsFragment {
            val args = Bundle()
            args.putString("from", from)
            val fragment = WalletDetailsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private val viewModal by lazy { ViewModelProvider(this)[ProfileViewModel::class.java] }

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentWalletDetailsBinding.inflate(layoutInflater)
    }

    private lateinit var loginResponse: LoginResponse
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
        binding.tvAddMoney.setOnClickListener(){
           showAddAmountDialog(requireActivity(),{amount,dialog->onAmountAdd(amount,dialog)})
        }
        binding.tvBankDetails.setOnClickListener {

           addFragmentToBackStack(
                BankDetailsFragment()
            )

        }
        binding.tvTransactionHistory.setOnClickListener {

          addFragmentToBackStack(
                TransactionHistoryFragment()
            )

        }


    }

    fun initView() {
        binding.viewHeader.getToolBarView().imgMenu1.isVisible=false
        binding.viewHeader.setTitle( requireActivity().getString(R.string.wallet_details))
    }

    private fun onAmountAdd(amount: String, dialog: BottomSheetDialog) {

        dialog.dismiss()
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


}