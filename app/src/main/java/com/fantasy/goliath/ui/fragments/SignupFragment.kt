package com.fantasy.goliath.ui.fragments

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer

import androidx.lifecycle.ViewModelProvider
import com.fantasy.goliath.R

import com.fantasy.goliath.databinding.FragmentSignUpBinding
import com.fantasy.goliath.model.LoginResponse
import com.fantasy.goliath.ui.base.BaseFragment
import com.fantasy.goliath.utility.Constants
import com.fantasy.goliath.utility.Debouncer
import com.fantasy.goliath.utility.getNumberFromString
import com.fantasy.goliath.utility.isNetworkConnected
import com.fantasy.goliath.utility.showOTPDialogBottom
import com.fantasy.goliath.utility.showToast


import com.fantasy.goliath.viewmodal.SignUpViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignupFragment : BaseFragment() {
    private val viewModal by lazy { ViewModelProvider(this)[SignUpViewModel::class.java] }
    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentSignUpBinding.inflate(layoutInflater)
    }


    val activityScope = CoroutineScope(Dispatchers.Main)
    lateinit var dialogVerify: BottomSheetDialog

    private var firebase_token = "test android"
    private var country_code = ""
    private var emailMobile = ""
    private var inputType = ""
    private var email = ""
    private var mobile = ""


    lateinit var mContext: Context

    lateinit var btnSubmit: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        mContext = requireActivity()


        binding.let {
            activityScope.launch {
                clickListener()
                getFirebaseRegId()
                initView()

            }
        }

        return binding.root
    }

    private fun initView() {
        val debouncer = Debouncer(100)
        binding.ediEmail.doOnTextChanged { text, _, _, _ ->

            email = text.toString()
            inputType = if (!TextUtils.isEmpty(email)) {
                "email"
            } else ""
            debouncer.debounce {
                if (binding.ediPhone.text.toString().trim().length > 0) {
                    binding.ediPhone.setText("")
                    binding.ediPhone.clearFocus()
                    inputType = "email"
                }
            }
        }
        binding.ediPhone.doOnTextChanged { text, _, _, _ ->
            mobile = text.toString()
            inputType = if (!TextUtils.isEmpty(mobile)) {
                "phone"
            } else {
                ""
            }
            debouncer.debounce {
                if (binding.ediEmail.text.toString().trim().length > 0) {
                    binding.ediEmail.setText("")
                    binding.ediEmail.clearFocus()
                    inputType = "phone"
                }
            }
        }
    }


    private fun clickListener() {


        binding.btnSubmit.setOnClickListener() {

            if (binding.ediName.text.toString().trim().isEmpty()) {
                showToast(mContext, getString(R.string.enter_your_name))
                binding.ediName.requestFocus()
            } else if (TextUtils.isEmpty(inputType)) {
                showToast(mContext, getString(R.string.enter_email_or_mobile))
                binding.ediPhone.requestFocus()
            } else if (inputType.equals("email") && binding.ediEmail.text.toString().isEmpty()) {
                showToast(mContext, getString(R.string.enter_email_address))
                binding.ediName.clearFocus()
                binding.ediEmail.requestFocus()
            } else if (inputType.equals("email") && !utilsManager.isValidEmailId(binding.ediEmail.text.toString())) {
                binding.ediName.clearFocus()
                showToast(mContext, getString(R.string.enter_valid_email_address))
                binding.ediEmail.requestFocus()
                binding.ediName.clearFocus()
            } else if (inputType.equals("phone") && binding.ediPhone.text.toString().isEmpty()) {
                showToast(mContext, getString(R.string.enter_phone_number))
                binding.ediEmail.clearFocus()
                binding.ediName.clearFocus()
                binding.ediPhone.requestFocus()
            } else if (inputType.equals("phone") && binding.ediPhone.text!!.length < 4) {
                binding.ediEmail.clearFocus()
                binding.ediName.clearFocus()
                showToast(mContext, getString(R.string.enter_valid_phone_number))
                binding.ediPhone.requestFocus()
            } else {

                emailMobile =
                    if (!TextUtils.isEmpty(binding.ediPhone.text.toString())) binding.ediPhone.text.toString() else binding.ediEmail.text.toString()
                binding.ediName.clearFocus()
                binding.ediPhone.clearFocus()
                binding.ediEmail.clearFocus()
                country_code = binding.countryPickerView.selectedCountryCode.toString()
               val mobile= getNumberFromString(emailMobile)
                if (!TextUtils.isEmpty(mobile)) {
                        if (!country_code.contains("+")) {
                            country_code = "+" + country_code
                        }
                  }else{
                      country_code=""
                  }
                callSignUpAPI()
            }


        }


        binding.imgBack.setOnClickListener() {
            onBackPressed()

        }
        binding.tvLogin.setOnClickListener() {

            onBackPressed()
        }

    }

    // and displays on the screen
    private fun getFirebaseRegId() {
        /*  FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
              if (!task.isSuccessful) {
                  Log.w("TAG", "Fetching FCM registration token failed", task.exception)
                  return@OnCompleteListener
              } else {
                  firebase_token = task.result
                  printLog("firebase_token", firebase_token.toString())
                  // Get new FCM registration token
              }
          })*/
    }

    private fun callSignUpAPI() {
        if (utilsManager.isNetworkConnected()) {
            viewModal.callSignUp(
                mContext,
                binding.ediName.text.toString().capitalize(),
                emailMobile,
                country_code, "register",
                firebase_token
            ).observe(viewLifecycleOwner,
                Observer { res ->
                    showToast(mContext, res.message)

                    if (res.status) {


                        showOTPDialogBottom(
                            mContext, false,country_code+" "+emailMobile,
                            { type, otp, dialog -> onOTPVerified(type, otp, dialog) })
                    }

                })
        }
    }

    private fun moveNextScreen() {
        preferenceManager.saveBoolean(Constants.KEY_CHECK_LOGIN, true)
        addFragment(RewardGuideFragment())
    }


    private fun onOTPVerified(type: String, otp: String, dialog: BottomSheetDialog) {
        dialogVerify = dialog

        if (type.equals("resend")) {
            callResendOTPAPI()
        } else {

            callVerifyOTPAPI(otp)
        }

    }


    private fun callVerifyOTPAPI(otp: String) {
        if (isNetworkConnected(requireActivity())) {
            viewModal.verifyUser(
                mContext,
                binding.ediName.text.toString().capitalize(),
                emailMobile,
                country_code,
                otp,"register" ,firebase_token
            ).observe(viewLifecycleOwner, androidx.lifecycle.Observer { res ->
                showToast(mContext, res.message)
                if (res.status) {

                    preferenceManager.saveAuthToken("${res.data.token_type} ${res.data.access_token}")
                    preferenceManager.saveUserName(res.data.user.full_name)
                    preferenceManager.setLoginData(res.data.user)
                    dialogVerify.dismiss()
                    moveNextScreen()

                }else{
                    utilsManager.showAlertMessageError(res.message)
                }

            })
        }
    }

    private fun callResendOTPAPI() {
        if (utilsManager.isNetworkConnected()) {

            viewModal.callSignUp(
                mContext,
                binding.ediName.text.toString().capitalize(),
                emailMobile,
                country_code, "register",
                firebase_token
            ).observe(viewLifecycleOwner,
                Observer { res ->
                    showToast(mContext, res.message)

                })
        }
    }


}
