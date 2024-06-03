package com.fantasy.goliath.ui.fragments

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer

import androidx.lifecycle.ViewModelProvider
import com.fantasy.goliath.R
import com.fantasy.goliath.databinding.FragmentWalletDetailsBinding
import com.fantasy.goliath.model.LoginResponse
import com.fantasy.goliath.model.UserDetails
import com.fantasy.goliath.ui.base.BaseFragment
import com.fantasy.goliath.utility.showAddAmountDialog

import com.fantasy.goliath.viewmodal.ProfileViewModel
import com.fantasy.goliath.viewmodal.WalletViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.JsonObject

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

    private val viewModal by lazy { ViewModelProvider(this)[WalletViewModel::class.java] }

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentWalletDetailsBinding.inflate(layoutInflater)
    }
    lateinit var dialogWallet: BottomSheetDialog
    private lateinit var userDetails: UserDetails
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding.let {
            userDetails=  preferenceManager.getLoginData()!!

            initView()
            setUIData()
            clickListener()
            callProfileInfoAPI()
        }
        return binding.root
    }

    private fun setUIData() {
        binding.tvAvailableAmount.text=getString(R.string.currency_symbol)+" "+userDetails.wallet
        if (userDetails.wallet_detail!=null){

            binding.tvDepositAmount.text=getString(R.string.currency_symbol)+" "+userDetails.wallet_detail.total_diposite
            binding.tvWithdrawAmount.text=getString(R.string.currency_symbol)+" "+userDetails.wallet_detail.total_withdrawal
        }
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
        dialogWallet=dialog

        callAddBalanceInWalletAPI(amount)
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
    private fun callAddBalanceInWalletAPI(amount: String) {

        if (utilsManager.isNetworkConnected()) {
            val json = JsonObject()
            json.addProperty("amount", amount)

            viewModal.addWalletAmountList(
                requireActivity(), preferenceManager.getAuthToken(), json
            ).observe(viewLifecycleOwner, androidx.lifecycle.Observer { res ->
                showErrorToast(res.message)

                if (res.status) {
                    dialogWallet.dismiss()
                    binding.tvAvailableAmount.text=Html.fromHtml(res.data.wallet_balance_show)
                    callProfileInfoAPI()
                }


            })
        }


    }
    fun callProfileInfoAPI() {
        if (utilsManager.isNetworkConnected()) {
            viewModal.getUserInfo(
                requireActivity(), preferenceManager.getAuthToken(), "GET", ""
            ).observe(viewLifecycleOwner, Observer { res ->

                if (res.status) {
                    userDetails = res.data.user
                    userDetails.wallet_detail = res.data.wallet_detail
                    preferenceManager.setLoginData(userDetails)
                    setUIData()
                }


            })
        }
    }

}