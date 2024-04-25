package com.fantasy.goliath.ui.fragments


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import com.fantasy.goliath.databinding.FragmentLoginBinding
import com.fantasy.goliath.model.LoginResponse
import com.fantasy.goliath.ui.base.BaseFragment
import com.fantasy.goliath.utility.Constants
import com.fantasy.goliath.utility.isNetworkConnected

import com.fantasy.goliath.utility.showOTPDialogBottom
import com.fantasy.goliath.utility.showToast
import com.fantasy.goliath.viewmodal.LoginViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginFragment : BaseFragment() {
    private val viewModal by lazy { ViewModelProvider(this)[LoginViewModel::class.java] }
    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentLoginBinding.inflate(layoutInflater)
    }


    val activityScope = CoroutineScope(Dispatchers.Main)
    lateinit var dialogVerify: BottomSheetDialog


    var isLogin = false
    var isForgotPassword = false
    private var firebase_token = ""
    private var email = ""

    var cardId = ""
    lateinit var stripKey:String
    lateinit var customerStripId:String

    lateinit var loginResponse: LoginResponse
    lateinit var mContext:Context
    lateinit var pgBar: ProgressBar
    lateinit var btnSubmit: Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        mContext=requireActivity()


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


    }


    private fun clickListener() {
        binding.btnSubmit.setOnClickListener() {
            isForgotPassword=false
            showOTPDialogBottom(mContext,true,{ type, otp, dialog -> onOTPVerified(type, otp, dialog) })



        }
        binding.tvSignUp.setOnClickListener() {
            addFragmentToBackStack(SignupFragment())

        }
       /* binding.btnLogin.setOnClickListener() {
            isLogin = true
            if (binding.edEmail.text.isEmpty()) {
                showToast(mContext, getString(R.string.enter_email_address))
                binding.edEmail.requestFocus()
            } else if (!utilsManager.isValidEmailId(binding.edEmail.text.toString())) {
                showToast(mContext, getString(R.string.enter_valid_email_address))
                binding.edEmail.requestFocus()
            } else if (binding.edPassword.text.isEmpty()) {
                showToast(mContext, getString(R.string.enter_password))
                binding.edPassword.requestFocus()
            } else {
                email=binding.edEmail.text.toString()
                binding.edEmail.clearFocus()
                binding.edPassword.clearFocus()
                isLogin=true
                callLoginAPI()
            }


        }
        binding.tvForgotPassword.setOnClickListener() {
            isLogin = false
            showForgotPasswordBottomSheet()


        }
        */

       /* binding.tvSignUp.setOnClickListener() {
            startActivity(Intent(mContext, SignupFragment::class.java))

        }*/

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

    private fun callLoginAPI() {
     /*   if (utilsManager.isNetworkConnected()) {
            viewModal.getLogInData(
                mContext,
                binding.edEmail.text.toString(),
                binding.edPassword.text.toString(),
                firebase_token
            ).observe(this,
                Observer { res ->
                    showToast(mContext, res.message)
                    loginResponse=res
                    if (res.status) {

                        preferenceManager.setLoginData(res)
                        preferenceManager.saveAuthToken("${loginResponse.token_type} ${loginResponse.access_token}")
                        if (TextUtils.isEmpty(loginResponse.user.email_verified_at)||loginResponse.user.email_verified_at.equals("0")) {
                            utilsManager.showOTPDialogBottom(
                                mContext, true,
                                { type, otp, dialog -> onOTPVerified(type, otp, dialog) })
                        }else if (loginResponse.user.is_payment.equals("1")) {
                            moveNextScreen()
                        }else{
                            callPlanListAPI()
                        }


                    }else{
                        if (res.message.equals("Please verify your email.",true)){
                            utilsManager.showOTPDialogBottom(
                                mContext, true,
                                { type, otp, dialog -> onOTPVerified(type, otp, dialog) })
                        }

                    }

                })
        }*/
    }














    private fun moveNextScreen() {

        preferenceManager.saveBoolean(Constants.KEY_CHECK_LOGIN, true)
        addFragmentToBackStack( RewardGuideFragment())

    }






    private fun onOTPVerified(type: String, otp: String, dialog: BottomSheetDialog) {
        dialogVerify = dialog

        if (type.equals("resend")) {
          //  callRequestOTPAPI()
        } else {
            dialogVerify.dismiss()
           moveNextScreen()
           // callVerifyOTPAPI(otp)
        }

    }

    private fun callVerifyOTPAPI(otp: String) {
        if (isNetworkConnected(requireActivity())) {
            viewModal.verifyUser(
                mContext,
                email,
                otp
            ).observe(this, androidx.lifecycle.Observer { res ->
                showToast(mContext, res.message)
                if (res.status) {
                    if (isLogin){

                        loginResponse = res
                        preferenceManager.saveAuthToken("${loginResponse.token_type} ${loginResponse.access_token}")
                        loginResponse.user.email_verified_at="1"
                        preferenceManager.setLoginData(loginResponse)
                        if (loginResponse.user.is_payment.equals("1")) {
                            dialogVerify.dismiss()
                            moveNextScreen()
                        }
                    }else{

                        dialogVerify.dismiss()
                    }



                }

            })
        }
    }

    private fun callRequestOTPAPI() {
        if (isNetworkConnected(requireActivity())) {
            viewModal.requestOTP(
                mContext,
                email,
            ).observe(this, androidx.lifecycle.Observer { res ->
                showToast(mContext, res.message)
                if (res.status) {
                    if (isForgotPassword) {
                        showOTPDialogBottom(
                            mContext, true,
                            { type, otp, dialog -> onOTPVerified(type, otp, dialog) })

                    }
                }
            })
        }
    }




}
