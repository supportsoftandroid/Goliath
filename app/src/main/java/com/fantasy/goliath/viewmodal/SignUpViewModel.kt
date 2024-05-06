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
        val json = JsonObject()
        //  val mobile=  mobileEmail.replace("[^0-9]".toRegex(), "")
        val mobile = getNumberFromString(mobileEmail)

        json.addProperty("full_name", name)
        if (!TextUtils.isEmpty(mobile)) {
            json.addProperty("country_code", country_code)
            json.addProperty("phone", mobileEmail)
        } else {
            json.addProperty("email", mobileEmail)
        }

        json.addProperty("type", type)
        json.addProperty("device_token", device_token)

        return AuthRepository(context).loginSignup(json)

    }

    fun verifyUser(
        context: Context,
        name: String,
        mobileEmail: String,
        country_code: String,
        otp: String,
        type: String,
        device_token: String
    ): LiveData<LoginResponse> {
        val json = JsonObject()
        val mobile = getNumberFromString(mobileEmail)
        json.addProperty("full_name", name)
        if (!TextUtils.isEmpty(mobile)) {
            json.addProperty("country_code", country_code)
            json.addProperty("phone", mobileEmail)
        } else {
            json.addProperty("email", mobileEmail)
        }

        json.addProperty("otp", otp)
        json.addProperty("type", type)
        json.addProperty("device_token", device_token)


        return AuthRepository(context).verifyOTP(json)

    }

    fun callResendOTP(
        context: Context,
        mobileEmail: String,
        country_code: String,
        type: String,
        device_token: String
    ): LiveData<LoginResponse> {
        val json = JsonObject()

        val mobile = mobileEmail.replace("[^0-9]".toRegex(), "")
        if (!TextUtils.isEmpty(mobile)) {
            json.addProperty("country_code", country_code)
            json.addProperty("phone", mobileEmail)
        } else {
            json.addProperty("email", mobileEmail)
        }

        json.addProperty("type", type)
        json.addProperty("push_token", device_token)
        return AuthRepository(context).loginSignup(json)

    }

}