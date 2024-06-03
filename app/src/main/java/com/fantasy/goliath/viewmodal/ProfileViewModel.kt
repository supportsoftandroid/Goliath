package com.fantasy.goliath.viewmodal

import android.content.Context
import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

import com.fantasy.goliath.model.LoginResponse
import com.fantasy.goliath.model.QuestionSaveRes
import com.fantasy.goliath.network.repository.AuthRepository
import com.fantasy.goliath.network.repository.RemoteRepository
import com.fantasy.goliath.utility.getNumberFromString
import com.google.gson.JsonObject

class ProfileViewModel : ViewModel() {

    fun getUserInfo(
        context: Context,
        token: String,
        type: String,
        imageFilePath: String
    ): LiveData<LoginResponse> {
        return AuthRepository(context).getProfile(token, type, imageFilePath)

    }
    fun profilePicUpdate(
        context: Context,
        token: String,
        type: String,
        imageFilePath: String
    ): LiveData<LoginResponse> {
        return AuthRepository(context).getProfile(token, type, imageFilePath)

    }

    fun updateProfile(
        context: Context,
        token: String,
        jsonObject: JsonObject
    ): LiveData<LoginResponse> {
        return AuthRepository(context).updateProfile(token, jsonObject)

    }
    fun addWalletAmountList(context: Context, token: String,   json: JsonObject): LiveData<QuestionSaveRes> {
        return RemoteRepository(context).addWalletAmountList(token, json)

    }

    fun requestOTP(
        context: Context, mobileEmail: String,
        country_code: String,
        type: String,
        device_token: String
    ): LiveData<LoginResponse> {
        val json = JsonObject()
        val mobile = getNumberFromString(mobileEmail)
        if (!TextUtils.isEmpty(mobile)) {
            json.addProperty("country_code", country_code)
            json.addProperty("phone", mobileEmail)
        } else {
            json.addProperty("email", mobileEmail)
        }

        json.addProperty("type", type)
        json.addProperty("device_token", device_token)
        return AuthRepository(context).updateEmailMobileOTP(json)

    }

    fun verifyOTP(
        context: Context, mobileEmail: String,
        country_code: String,
        type: String,
        otp: String,
        device_token: String
    ): LiveData<LoginResponse> {
        val json = JsonObject()
        val mobile = getNumberFromString(mobileEmail)
        if (!TextUtils.isEmpty(mobile)) {
            json.addProperty("country_code", country_code)
            json.addProperty("phone", mobileEmail)
        } else {
            json.addProperty("email", mobileEmail)
        }
        json.addProperty("type", type)
        json.addProperty("otp", otp)
        json.addProperty("device_token", device_token)
        return AuthRepository(context).verifyOTP(json)

    }

}