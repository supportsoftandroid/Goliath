package com.fantasy.goliath.ui.fragments.nav

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.fantasy.goliath.R

import com.fantasy.goliath.databinding.FragmentProfileBinding
import com.fantasy.goliath.databinding.ListCommonItemBinding
import com.fantasy.goliath.model.CommonDataItem
import com.fantasy.goliath.model.UserDetails
import com.fantasy.goliath.ui.fragments.StaticPagesFragment
import com.fantasy.goliath.ui.adapter.ProfileAdapter
import com.fantasy.goliath.ui.base.BaseFragment
import com.fantasy.goliath.ui.fragments.*
import com.fantasy.goliath.utility.Constants
import com.fantasy.goliath.utility.Constants.EDIT_PROFILE_REQUEST_KEY
import com.fantasy.goliath.utility.DialogManager
import com.fantasy.goliath.utility.IMAGE_CROP_REQUEST_CODE
import com.fantasy.goliath.utility.logoutFromApp
import com.fantasy.goliath.utility.printLog

import com.fantasy.goliath.utility.showAddAmountDialog
import com.fantasy.goliath.utility.showGallaryBottomModelSheet
import com.fantasy.goliath.utility.showToast

import com.fantasy.goliath.viewmodal.ProfileViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.JsonObject
import com.health.kharma.ui.adapters.MyAdapter

class ProfileFragment : BaseFragment() {
    private val viewModal by lazy { ViewModelProvider(this)[ProfileViewModel::class.java] }

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentProfileBinding.inflate(layoutInflater)
    }

    lateinit var adaper: ProfileAdapter
    var dataList = mutableListOf<CommonDataItem>()
    var dataListOther = mutableListOf<CommonDataItem>()

    private lateinit var userDetails: UserDetails
    lateinit var myAdapter: MyAdapter<CommonDataItem>
    lateinit var dialogManager: DialogManager
    lateinit var dialogWallet: BottomSheetDialog
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding.let {
            dialogManager = DialogManager(requireActivity())
            initView()
            clickListener()
            callProfileInfoAPI()
        }
        return binding.root
    }

    private fun clickListener() {
        binding.imgCamera.setOnClickListener {
            showGallaryBottomModelSheet(requireActivity(), dialogManager)
        }
        binding.tvAddMoney.setOnClickListener() {
            showAddAmountDialog(requireActivity(),
                { amount, dialog -> onAmountAdd(amount, dialog) })
        }
        binding.viewHeader.imgMenu1.setOnClickListener() {

            onWalletIconClick()

        }
        binding.viewHeader.imgMenu2.setOnClickListener() {
            onNotificationsIconClick()

        }
        binding.tvWalletLabel.setOnClickListener() {
         //   onWalletIconClick()
        }
        binding.llTotalDeposited.setOnClickListener() {
          //  onWalletIconClick()
        }
        binding.llTotalWinning.setOnClickListener() {
           // onWalletIconClick()
        }
        binding.llTotalFreePaid.setOnClickListener() {
           // onWalletIconClick()
        }

        binding.llTotalWithdraw.setOnClickListener() {
            onWalletIconClick()
        }

    }



    private fun onAmountAdd(amount: String, dialog: BottomSheetDialog) {

        dialogWallet=dialog
        callAddBalanceInWalletAPI(amount)
    }

    fun initView() {
        binding.viewHeader.txtTitle.text = requireActivity().getString(R.string.my_profile)
        binding.viewHeader.imgProfile.isVisible = false



        setProfileUIData()
        dataList.clear()
        dataList.add(CommonDataItem("My Predictions", "View for matches", false))
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
        dataListOther.add(CommonDataItem(getString(R.string.logout), "logout", false))


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
                            addFragmentToBackStack( StaticPagesFragment.newInstance(
                                data.title,
                                data.type
                            ))

                        }
                    }
                }


            }
        }
        binding.rvList2.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvList2.adapter = myAdapter
        setFragmentResultListener(EDIT_PROFILE_REQUEST_KEY) { key, bundle ->
            // Any type can be passed via to the bundle
            val from = bundle.getString("from").toString()
            if (from.equals("edit_profile")) {
                setProfileUIData()
            }
            // Do something with the result...
        }

    }

    private fun setProfileUIData() {
        userDetails = preferenceManager.getLoginData()!!
        loadImage(userDetails.avatar_full_path, binding.imgProfiles)
        binding.tvName.setText(userDetails.full_name)

        if (!TextUtils.isEmpty(userDetails.email)) binding.tvEmail.setText(userDetails.email) else binding.tvEmail.setText(
            userDetails.country_code + " " + userDetails.phone
        )

        binding.tvTotalBalance.text=getString(R.string.currency_symbol)+" "+userDetails.wallet
        loadImage(userDetails.avatar_full_path)
        if (userDetails.wallet_detail!=null){

            binding.tvTotalDepositAmount.text=getString(R.string.currency_symbol)+" "+userDetails.wallet_detail.total_diposite
            binding.tvTotalWinningAmount.text=getString(R.string.currency_symbol)+" "+userDetails.wallet_detail.total_winning
            binding.tvFeePaid.text=getString(R.string.currency_symbol)+" "+userDetails.wallet_detail.total_fee_paid
            binding.tvWithdraw.text=getString(R.string.currency_symbol)+" "+userDetails.wallet_detail.total_withdrawal
        }
    }

    private fun onAdapterClick(pos: Int, type: String) {
        if (type.equals("My Predictions", true)) {

            addFragmentToBackStack(
                MatchOngoingFragment.newInstance("profile")
            )

        } else if (type.equals("My info & Settings")) {

            addFragmentToBackStack(
                EditProfileFragment.newInstance("add")
            )
        } else if (type.equals("Transaction History")) {

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

    fun callProfileImageUploadAPI(imagefilePath: String) {
        if (utilsManager.isNetworkConnected()) {
            viewModal.profilePicUpdate(
                requireActivity(), preferenceManager.getAuthToken(), "Post", imagefilePath
            ).observe(viewLifecycleOwner, Observer { res ->
                showToast(requireActivity(), res.message)
                if (res.status) {
                    userDetails = res.data.user
                    preferenceManager.setLoginData(userDetails)
                    val bundle = Bundle()
                    bundle.putString("from", "edit_profile")

                    parentFragmentManager.setFragmentResult(
                        Constants.EDIT_PROFILE_OTHER_REQUEST_KEY,
                        bundle
                    )
                    setProfileUIData()
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
                    setProfileUIData()
                }


            })
        }
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
                    binding.tvTotalBalance.text= Html.fromHtml(res.data.wallet_balance_show)
                    callProfileInfoAPI()
                }


            })
        }


    }

    fun loadImage(pathUrl: String) {
        printLog("pathUrl", pathUrl)
        Glide.with(requireActivity())
            .load(pathUrl)
            .apply(
                RequestOptions().placeholder(R.drawable.ic_loading)
                    .error(R.drawable.dummy_profile)
                    .transform(CenterCrop(), RoundedCorners(190))
            ).into(binding.imgProfiles)

    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == IMAGE_CROP_REQUEST_CODE) {
            dialogManager.dismissDialog()
            if (resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK
                val imagefileUri = data?.data!!
                if (imagefileUri != null) {
                    val imagefilePath = imagefileUri?.path.toString()
                    loadImage(imagefilePath)
                    callProfileImageUploadAPI(imagefilePath)
                }

            } else if (resultCode == com.github.dhaval2404.imagepicker.ImagePicker.RESULT_ERROR) {
                Toast.makeText(
                    context,
                    com.github.dhaval2404.imagepicker.ImagePicker.getError(data),
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else {
                showToast(requireActivity(), "Task Cancelled")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }
}