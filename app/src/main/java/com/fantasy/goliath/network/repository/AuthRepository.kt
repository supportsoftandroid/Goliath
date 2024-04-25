package com.fantasy.goliath.network.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.fantasy.goliath.R
import com.fantasy.goliath.model.LoginResponse
import com.fantasy.goliath.network.RetrofitClient
import com.fantasy.goliath.utility.Constants
import com.fantasy.goliath.utility.Constants.SOMETHING_WENT_WRONG_ERROR
import com.fantasy.goliath.utility.DialogManager
import com.fantasy.goliath.utility.InvalidSession
import com.fantasy.goliath.utility.prepareFilePartFromUri
import com.fantasy.goliath.utility.showToast

import com.google.gson.JsonObject
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthRepository(
    mContext: Context,
) {

    val progressDialog: DialogManager
    var context: Context

    init {
        context = mContext
        progressDialog = DialogManager(context)
    }

    fun signUp(
        name: String,
        email: String,
        country_code: String,
        mobile: String,
        password: String,
        device_token: String): MutableLiveData<LoginResponse> {
        val jsonObject = JsonObject()
        jsonObject.addProperty("name", name)

        jsonObject.addProperty("email", email)
        jsonObject.addProperty("country_code", country_code)
        jsonObject.addProperty("mobile_number", mobile)
        jsonObject.addProperty("password", password)
        jsonObject.addProperty("device_type", Constants.DEVICE_TYPE)
        jsonObject.addProperty("device_token", device_token)
        val call = RetrofitClient.apiInterface.signUp(jsonObject)
        setProgressDialog()
        return callAPIService(call);
    }

    fun userLogin(
        mobile: String,
        password: String,
        device_token: String
    ): MutableLiveData<LoginResponse> {
        val jsonObject = JsonObject()
        jsonObject.addProperty("email", mobile)
        jsonObject.addProperty("password", password)
        jsonObject.addProperty("device_type", Constants.DEVICE_TYPE)
        jsonObject.addProperty("push_token", device_token)
        val call = RetrofitClient.apiInterface.userLogin(jsonObject)
        setProgressDialog()
        return callAPIService(call);

    }

    fun verifyOTP(jsonObject: JsonObject): MutableLiveData<LoginResponse> {
        val call = RetrofitClient.apiInterface.verify_otp(jsonObject)
        setProgressDialog()
        return callAPIService(call);
    }

    fun requestOTP(
        email: String, type: String
    ): MutableLiveData<LoginResponse> {
        val jsonObject = JsonObject()

        jsonObject.addProperty("email", email)
        jsonObject.addProperty("type", type)
        val call = RetrofitClient.apiInterface.request_otp(jsonObject)
        setProgressDialog()
        return callAPIService(call);
    }

    fun resetPassword(
        email: String,
        password: String

    ): MutableLiveData<LoginResponse> {
        val jsonObject = JsonObject()
        jsonObject.addProperty("email", email)
        jsonObject.addProperty("password", password)

        val call = RetrofitClient.apiInterface.request_otp(jsonObject)
        setProgressDialog()
        return callAPIService(call);
    }

    fun getProfile(
        userToken: String,
        type: String?,
        imageFilePath: String?
    ): MutableLiveData<LoginResponse> {
        var callApiService: Call<LoginResponse>? = null
        if (type == "get") {
            callApiService = RetrofitClient.apiInterface.getProfile(userToken)
            return callAPIService(callApiService)
        } else {
            val imageFile: MultipartBody.Part? =
                imageFilePath?.let { prepareFilePartFromUri("image", it) }
            callApiService = RetrofitClient.apiInterface.updateProfileImage(
                userToken,
                imageFile
            )
            return callAPIService(callApiService)
        }

    }

    fun updateProfile(
        userToken: String,
        name: String,
        email: String,
        country_code: String,
        mobile: String,
    ): MutableLiveData<LoginResponse> {
        val jsonObject = JsonObject()
        jsonObject.addProperty("name", name)

        jsonObject.addProperty("email", email)
        jsonObject.addProperty("country_code", country_code)
        jsonObject.addProperty("mobile_number", mobile)
        val callApiService = RetrofitClient.apiInterface.updateProfile(
            userToken,
            jsonObject
        )
        return callAPIService(callApiService)
    }

    fun callAPIService(call: Call<LoginResponse>): MutableLiveData<LoginResponse> {
        val modelRes = MutableLiveData<LoginResponse>()
        makeApiCall(call, onSuccess = { data ->
            // Handle successful response
            // data is of type MyData or null
            val model: LoginResponse? = data
            if (model != null) {
                modelRes.value = model!!
            }

        },
            onError = { error ->
                // Handle error
            })

        return modelRes
        return modelRes

    }
    fun <T> makeApiCall(call: Call<T>, onSuccess: (T?) -> Unit, onError: (String) -> Unit) {

        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                progressDialog.dismissDialog()
                if (response.isSuccessful) {
                    onSuccess(response.body())
                }else if (response.code()==401) {
                    InvalidSession(context, response.message())
                }  else {
                    showToast(context, SOMETHING_WENT_WRONG_ERROR)
                    showToast(context, response.message(),)

                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                progressDialog.dismissDialog()
                showToast(context, t.message.toString())

            }
        })
    }
    private fun setProgressDialog() {

        progressDialog.showProgressDialog(context.getString(R.string.loading))
    }
}


