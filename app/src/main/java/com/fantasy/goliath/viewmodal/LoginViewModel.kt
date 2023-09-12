package com.fantasy.goliath.viewmodal

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import com.fantasy.goliath.model.*
import com.fantasy.goliath.network.repository.*


class LoginViewModel : ViewModel() {

    fun getLogInData(
        context: Context,
        mobile: String,
        password: String,

        device_token: String
    ): LiveData<LoginResponse> {
       return AuthRepository(context).userLogin(mobile, password, device_token)

    }


    fun addCard(
        context: Context,
        authToken: String,
        customer_id: String,
        card_id: String,
        strName: String
    ): LiveData<CardResponse> {
        return StripCardRepository(context).addCard(authToken, customer_id, card_id, strName)

    }



    fun verifyUser(context: Context, email: String, otp: String): LiveData<LoginResponse> {
        val jsonObject = JsonObject()
        // val jsonString = Gson().toJson(userDetails)  // json string
        jsonObject.addProperty("email", email)
        jsonObject.addProperty("otp", otp)

      return AuthRepository(context).verifyOTP(jsonObject)

    }

    fun requestOTP(context: Context, email: String): LiveData<LoginResponse> {
       return AuthRepository(context).requestOTP(email, "forgot")

    }

    fun resetPassword(context: Context, email: String, password: String): LiveData<LoginResponse> {
       return AuthRepository(context).resetPassword(email, password)

    }
}