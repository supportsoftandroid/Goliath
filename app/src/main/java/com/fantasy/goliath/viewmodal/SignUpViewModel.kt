package com.fantasy.goliath.viewmodal

import android.content.Context
import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.fantasy.goliath.model.CardResponse
import com.fantasy.goliath.model.LoginResponse
import com.fantasy.goliath.network.repository.AuthRepository
import com.fantasy.goliath.network.repository.StripCardRepository
import com.fantasy.goliath.utility.Constants
import com.fantasy.goliath.utility.getNumberFromString
import com.google.gson.JsonObject


class SignUpViewModel : ViewModel() {

    fun callSignUp(
        context: Context,
        name: String,
        mobileEmail: String,
        country_code: String,
        type: String,
        device_token: String
    ): LiveData<LoginResponse> {
        val jsonObject=JsonObject()
      //  val mobile=  mobileEmail.replace("[^0-9]".toRegex(), "")
        val mobile= getNumberFromString(mobileEmail)

        jsonObject.addProperty("full_name", name)
        if (!TextUtils.isEmpty(mobile)) {
            jsonObject.addProperty("country_code", country_code)
            jsonObject.addProperty("phone", mobileEmail)
        }else{
            jsonObject.addProperty("email", mobileEmail)
        }

        jsonObject.addProperty("type", type)
        jsonObject.addProperty("device_token", device_token)

        return AuthRepository(context).loginSignup( jsonObject)

    }

    fun verifyUser(context: Context,  mobileEmail: String,  country_code: String, otp: String, device_token: String): LiveData<LoginResponse> {
        val jsonObject = JsonObject()
        val mobile= getNumberFromString(mobileEmail)
        if (!TextUtils.isEmpty(mobile)) {
            jsonObject.addProperty("country_code", country_code)
            jsonObject.addProperty("phone", mobileEmail)
        }else{
            jsonObject.addProperty("email", mobileEmail)
        }

        jsonObject.addProperty("otp", otp)
        jsonObject.addProperty("device_token", device_token)


        return AuthRepository(context).verifyOTP(jsonObject)

    }
    fun callResendOTP(
        context: Context,
        mobileEmail: String,
        country_code: String,
        type: String,
        device_token: String
    ): LiveData<LoginResponse> {
        val jsonObject=JsonObject()

        val mobile=  mobileEmail.replace("[^0-9]".toRegex(), "")
        if (!TextUtils.isEmpty(mobile)) {
            jsonObject.addProperty("country_code", country_code)
            jsonObject.addProperty("phone", mobileEmail)
        }else{
            jsonObject.addProperty("email", mobileEmail)
        }

        jsonObject.addProperty("type", type)
        jsonObject.addProperty("push_token", device_token)
        return AuthRepository(context).loginSignup( jsonObject)

    }

}