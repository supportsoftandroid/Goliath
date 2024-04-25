package com.fantasy.goliath.network.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.fantasy.goliath.R
import com.fantasy.goliath.model.CardListResponse
import com.fantasy.goliath.model.CardResponse
import com.fantasy.goliath.network.RetrofitClient
import com.fantasy.goliath.utility.Constants
import com.fantasy.goliath.utility.DialogManager
import com.fantasy.goliath.utility.InvalidSession
import com.fantasy.goliath.utility.printLog
import com.fantasy.goliath.utility.showToast

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StripCardRepository(
    mContext: Context
) {
    val responseData = MutableLiveData<CardListResponse>()
    val resAddCard = MutableLiveData<CardResponse>()
    val progressDialog: DialogManager
    var context: Context

    init {
        context = mContext
        progressDialog = DialogManager(context)
    }

    fun addCard(
        authToken: String, customer_token: String, card_token: String, strName: String,

        ): MutableLiveData<CardResponse> {
        val jsonObject = JsonObject()
        jsonObject.addProperty("customer_token", customer_token)
        jsonObject.addProperty("card_token", card_token)
        jsonObject.addProperty("name", strName)
        val call = RetrofitClient.apiInterface.addCard(authToken, jsonObject)

        return callAddCardAPIService(call)
    }

    fun deleteCard(
        authToken: String,
        customer_token: String,
        card_token: String
    ): MutableLiveData<CardResponse> {
        val jsonObject = JsonObject()
        jsonObject.addProperty("customer_token", customer_token)
        jsonObject.addProperty("card_token", card_token)
        val call = RetrofitClient.apiInterface.deleteCard(authToken, jsonObject)

        return callAddCardAPIService(call)
    }

    fun getCardList(isProgress: Boolean,authToken: String, customer_token: String): MutableLiveData<CardListResponse> {
        val jsonObject = JsonObject()
        jsonObject.addProperty("customer_token", customer_token)

        val call = RetrofitClient.apiInterface.getCardList(authToken, jsonObject)
        setProgressDialog()
        if (!isProgress){
            progressDialog.dismissDialog()
        }
        return callAPIService(call)
    }

    private fun callAPIService(call: Call<CardListResponse>): MutableLiveData<CardListResponse> {


        call.enqueue(object : Callback<CardListResponse> {
            override fun onResponse(call: Call<CardListResponse>, response: Response<CardListResponse>) {
                progressDialog.dismissDialog()
                if (response.isSuccessful) {
                    val model: CardListResponse? = response.body()
                    if (model != null) {
                        responseData.value = model!!
                    } else {
                        showToast(context, Constants.SOMETHING_WENT_WRONG_ERROR)
                        showToast(context, response.message())
                    }
                } else if (response.code() == 401) {
                    InvalidSession(context, response.message())

                } else {
                    showToast(context, response.message())
                }

            }

            override fun onFailure(call: Call<CardListResponse>, t: Throwable) {
                progressDialog.dismissDialog()
                showToast(context, t.message.toString())
            }
        })
        return responseData;
    }

    private fun callAddCardAPIService(call: Call<CardResponse>): MutableLiveData<CardResponse> {

        setProgressDialog()
        call.enqueue(object : Callback<CardResponse> {
            override fun onResponse(call: Call<CardResponse>, response: Response<CardResponse>) {
                progressDialog.dismissDialog()

                if (response.isSuccessful) {
                    val model: CardResponse? = response.body()
                    if (model != null) {
                        resAddCard.value = model!!
                    } else {
                        showToast(context, Constants.SOMETHING_WENT_WRONG_ERROR)
                        showToast(
                            context, response.message(),
                        )
                    }
                } else if (response.code() == 401) {
                    InvalidSession(context, response.message())

                } else {
                    showToast(context, response.message())
                }

            }

            override fun onFailure(call: Call<CardResponse>, t: Throwable) {
                progressDialog.dismissDialog()
                showToast(context, t.message.toString())
                printLog("error", t.message.toString())
            }
        })
        return resAddCard;
    }

    private fun setProgressDialog() {
        progressDialog.showProgressDialog(context.getString(R.string.loading))
    }
}


