package com.fantasy.goliath.ui.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.fantasy.goliath.R
import com.fantasy.goliath.databinding.FragmentEditProfileBinding
import com.fantasy.goliath.model.UserDetails
import com.fantasy.goliath.ui.base.BaseFragment
import com.fantasy.goliath.utility.Constants.EDIT_PROFILE_OTHER_REQUEST_KEY
import com.fantasy.goliath.utility.Constants.EDIT_PROFILE_REQUEST_KEY
import com.fantasy.goliath.utility.getNumberFromString
import com.fantasy.goliath.utility.showToast
import com.fantasy.goliath.utility.showUpdateEmailMobileBottom
import com.fantasy.goliath.utility.showVerifyOTPEmailMobileBottom
import com.fantasy.goliath.viewmodal.ProfileViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.JsonObject

class EditProfileFragment : BaseFragment() {
    companion object {
        fun newInstance(from: String): EditProfileFragment {
            val args = Bundle()
            args.putString("from", from)
            val fragment = EditProfileFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private val viewModal by lazy { ViewModelProvider(this)[ProfileViewModel::class.java] }

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentEditProfileBinding.inflate(layoutInflater)
    }

    var updateType = ""
    var emailMobile = ""
    var country_code = ""
    var isResend = false
    lateinit var dialogUpdate: BottomSheetDialog
    lateinit var dialogVerifyOTP: BottomSheetDialog
    lateinit var userDetails: UserDetails

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        val root: View = binding.root

        binding.let {
            userDetails = preferenceManager.getLoginData()!!
            initView()
            clickListener()
        }
        return root
    }

    private fun clickListener() {
        binding.viewHeader.setClickListener(this)

        binding.btnSubmit.setOnClickListener() {
            if (binding.ediName.text.toString().trim().isEmpty()) {
                showToast(requireActivity(), getString(R.string.enter_your_name))
                binding.ediName.requestFocus()
            } else {
                binding.ediName.clearFocus()
                val json = JsonObject()
                json.addProperty("full_name", binding.ediName.text.toString().capitalize())
                callUpdateProfileAPI(json)

            }

        }
        binding.ediEmail.setOnClickListener() {
            updateType = "email"
            showUpdateEmailMobileBottom(
                requireActivity(),
                false,
                updateType,
                { type, country_code, emailMobile, dlg ->
                    dialogUpdate = dlg
                    this.country_code = country_code
                    this.emailMobile = emailMobile
                    isResend = false
                    callResendOTPAPI()
                })
        }

        binding.ediPhone.setOnClickListener() {
            updateType = "phone"
            showUpdateEmailMobileBottom(
                requireActivity(),
                false,
                updateType,
                { type, country_code, emailMobile, dlg ->
                    dialogUpdate = dlg
                    this.country_code = country_code
                    this.emailMobile = emailMobile
                    isResend = false
                    callResendOTPAPI()
                })
        }

    }

    fun initView() {

        binding.viewHeader.setTitle(requireActivity().getString(R.string.edit_profile))
        setupUIData()
    }

    private fun setupUIData() {
        binding.ediEmail.setText(userDetails.email)
        binding.ediPhone.setText(userDetails.phone)
        binding.ediName.setText(userDetails.full_name)
        if (!TextUtils.isEmpty(userDetails.country_code)){
            binding.countryPickerView.setCountryForPhoneCode(userDetails.country_code.toInt())
        }

        binding.ediEmail.isVisible = !userDetails.email.isEmpty()
        binding.clvCountry.isVisible = !userDetails.phone.isEmpty()



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

    override fun onDestroyView() {
        super.onDestroyView()

    }

    private fun callResendOTPAPI() {
        if (utilsManager.isNetworkConnected()) {

            viewModal.requestOTP(
                requireActivity(),
                emailMobile,
                country_code, "update",
                "test"
            ).observe(viewLifecycleOwner,
                Observer { res ->
                    showToast(requireActivity(), res.message)
                    if (res.status) {
                    if (!isResend) {

                        showVerifyOTPEmailMobileBottom(requireActivity(),
                            false,
                            country_code +" "+ emailMobile,
                            { type, otp, dlg ->
                                dialogVerifyOTP = dlg
                                if (type.equals("verify")) {
                                    callVerifyOTPAPI(otp)
                                } else {
                                    isResend = true
                                    callResendOTPAPI()
                                }

                            })
                    }
                    }
                })
        }
    }

    private fun callVerifyOTPAPI(otp: String) {
        if (utilsManager.isNetworkConnected()) {
            viewModal.verifyOTP(
                requireActivity(),
                emailMobile,
                country_code, "update",
                otp, "jgjkfgfkgjjkg"
            ).observe(viewLifecycleOwner,
                Observer { res ->
                    showToast(requireActivity(), res.message)
                    if (res.status) {
                        val json = JsonObject()
                        val mobile = getNumberFromString(emailMobile)

                        if (!TextUtils.isEmpty(mobile)) {
                            json.addProperty("country_code", country_code)
                            json.addProperty("phone", emailMobile)
                        } else {
                            json.addProperty("email", emailMobile)
                        }
                        dialogUpdate.dismiss()
                        dialogVerifyOTP.dismiss()
                        callUpdateProfileAPI(json)
                    }


                })
        }
    }

    private fun callUpdateProfileAPI(json: JsonObject) {
        if (utilsManager.isNetworkConnected()) {

            viewModal.updateProfile(
                requireActivity(),
                preferenceManager.getAuthToken(), json

            ).observe(viewLifecycleOwner,
                Observer { res ->
                    showToast(requireActivity(), res.message)
                    if (res.status) {
                        userDetails = res.data.user
                        preferenceManager.setLoginData(userDetails)
                        setupUIData()
                        val bundle=Bundle()
                        bundle.putString("from", "edit_profile")
                        parentFragmentManager.setFragmentResult(
                            EDIT_PROFILE_REQUEST_KEY,
                            bundle
                        )
                        parentFragmentManager.setFragmentResult(
                            EDIT_PROFILE_OTHER_REQUEST_KEY,
                            bundle
                        )
                    }

                })
        }
    }
}