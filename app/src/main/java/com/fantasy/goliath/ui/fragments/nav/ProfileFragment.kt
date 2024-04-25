package com.fantasy.goliath.ui.fragments.nav

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fantasy.goliath.R

import com.fantasy.goliath.databinding.FragmentProfileBinding
import com.fantasy.goliath.databinding.ListCommonItemBinding
import com.fantasy.goliath.databinding.ListOverStatusItemBinding
import com.fantasy.goliath.model.CommonDataItem
import com.fantasy.goliath.model.LoginResponse
import com.fantasy.goliath.ui.activities.MainActivity
import com.fantasy.goliath.ui.activities.StaticPagesActivity
import com.fantasy.goliath.ui.adapter.ProfileAdapter
import com.fantasy.goliath.ui.base.BaseFragment
import com.fantasy.goliath.ui.fragments.*
import com.fantasy.goliath.utility.logoutFromApp

import com.fantasy.goliath.utility.showAddAmountDialog

import com.fantasy.goliath.viewmodal.ProfileViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.health.kharma.ui.adapters.MyAdapter

class ProfileFragment : BaseFragment() {
    private val viewModal by lazy { ViewModelProvider(this)[ProfileViewModel::class.java] }

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentProfileBinding.inflate(layoutInflater)
    }

    lateinit var adaper: ProfileAdapter
    var dataList = mutableListOf<CommonDataItem>()
    var dataListOther = mutableListOf<CommonDataItem>()

    private lateinit var loginResponse: LoginResponse
    lateinit var myAdapter: MyAdapter<CommonDataItem>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding.let {
            initView()
            clickListener()
        }
        return binding.root
    }

    private fun clickListener() {

        binding.tvAddMoney.setOnClickListener() {
            showAddAmountDialog(requireActivity(),
                { amount, dialog -> onAmountAdd(amount, dialog) })
        }
        binding.tvWalletLabel.setOnClickListener() {
            openWallet()
        }
        binding.llTotalDeposited.setOnClickListener() {
            openWallet()
        }
        binding.llTotalWinning.setOnClickListener() {
            openWallet()
        }
        binding.llTotalFreePaid.setOnClickListener() {
            openWallet()
        }

        binding.llTotalWithdraw.setOnClickListener() {
            openWallet()
        }

    }

    private fun openWallet() {
        MainActivity.hideNavigationTab()
        addFragmentToBackStack(

            WalletDetailsFragment.newInstance("add")
        )
    }

    private fun onAmountAdd(amount: String, dialog: BottomSheetDialog) {

        dialog.dismiss()
    }

    fun initView() {
        binding.viewHeader.txtTitle.text = requireActivity().getString(R.string.my_profile)
        binding.imgProfile.isVisible=false

        //setProfileData(loginResponse.user)
        dataList.clear()
        dataList.add(CommonDataItem("History", "View for matches", false))
        dataList.add(
            CommonDataItem(
                "My info & Settings",
                "Edit your info & additional information",
                false
            )
        )
        dataList.add(CommonDataItem("Transaction History", "View your past transaction", false))

        adaper =
            ProfileAdapter(requireActivity(), dataList, { pos, type -> onAdapterClick(pos, type) })
        binding.rvList.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvList.adapter = adaper

        //Data Other
        dataListOther.clear()
        dataListOther.add(CommonDataItem(getString(R.string.terms_amp_conditions), "terms", false))
        dataListOther.add(CommonDataItem(getString(R.string.privacy_policy), "privacy", false))
        dataListOther.add(CommonDataItem(getString(R.string.help_amp_support), "help", false))
        dataListOther.add(CommonDataItem(getString(R.string.about_us), "about", false))
        dataListOther.add(CommonDataItem(getString(R.string.terms_amp_conditions), "logout", false))

        myAdapter = MyAdapter(
            R.layout.list_common_item,
            dataListOther
        ) { view, data, pos ->
            ListCommonItemBinding.bind(view).apply {
                tvTitle.text = data.title
                clvMain.setOnClickListener {
                    when (data.type.lowercase()) {
                        "logout" -> {
                            logOutFromApp()
                        }
                        else -> {
                            StaticPagesActivity.newInstance(
                                requireActivity(),
                                data.title,
                                data.type
                            )
                        }
                    }
                }


            }
        }
        binding.rvList2.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvList2.adapter = myAdapter

    }

    private fun onAdapterClick(pos: Int, type: String) {
        if (type.equals("History", true)) {
            MainActivity.hideNavigationTab()
            addFragmentToBackStack(
                MatchHistoryFragment.newInstance("add")
            )

        } else if (type.equals("My info & Settings")) {
            MainActivity.hideNavigationTab()
            addFragmentToBackStack(
                EditProfileFragment.newInstance("add")
            )
        } else if (type.equals("Transaction History")) {
            MainActivity.hideNavigationTab()
            addFragmentToBackStack(
                TransactionHistoryFragment.newInstance("add")
            )
        }

    }

    private fun logOutFromApp() {
        val builder = AlertDialog.Builder(requireActivity())
        // builder.setTitle(R.string.app_name)
        builder.setMessage(R.string.logout_message)

        builder.setPositiveButton(getString(R.string.yes)) { dialogInterface, which ->
            logoutFromApp(requireActivity())


        }
        //performing negative action
        builder.setNegativeButton(getString(R.string.no)) { dialogInterface, which -> }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }
}