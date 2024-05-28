package com.fantasy.goliath.network.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.fantasy.goliath.R
import com.fantasy.goliath.model.HowToPlayResponse
import com.fantasy.goliath.model.LoginResponse
import com.fantasy.goliath.model.MatchDetailsData
import com.fantasy.goliath.model.MatchList
import com.fantasy.goliath.model.MatchesDetailsRes
import com.fantasy.goliath.model.MatchesResponse
import com.fantasy.goliath.model.OverResultDetailsRes
import com.fantasy.goliath.model.QuestionListRes
import com.fantasy.goliath.model.QuestionSaveRes
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

class RemoteRepository(
    mContext: Context,
) {

    val progressDialog: DialogManager
    var context: Context

    init {
        context = mContext
        progressDialog = DialogManager(context)
    }


    fun getHowToPlayList(
        userToken: String,
    ): MutableLiveData<HowToPlayResponse> {
        val json = JsonObject()

        val callApiService = RetrofitClient.apiInterface.how_to_play(
            userToken, json

        )
        setProgressDialog()
        return callHowToPlayAPIService(callApiService)
    }

    fun getMatchesList(
        userToken: String,
        page: Int,
        json: JsonObject,
    ): MutableLiveData<MatchesResponse> {
        val callApiService = RetrofitClient.apiInterface.getMatchesList(
            userToken, page, json

        )
        setProgressDialog()
        return callMatchAPIService(callApiService)
    }

    fun getMatchDetails(
        userToken: String,
        json: JsonObject,
    ): MutableLiveData<MatchesDetailsRes> {
        val callApiService = RetrofitClient.apiInterface.getMatchDetails(
            userToken, json

        )
        setProgressDialog()
        return callMatchDetailsAPIService(callApiService)
    }
  fun getQuestionsList(
        userToken: String,
        json: JsonObject,
    ): MutableLiveData<MatchesDetailsRes> {
        val callApiService = RetrofitClient.apiInterface.getQuestionList(
            userToken, json

        )
      setProgressDialog()
        return callMatchDetailsAPIService(callApiService)
    }
    fun saveQuestionsList(
        userToken: String,
        json: JsonObject,
    ): MutableLiveData<QuestionSaveRes> {
        val callApiService = RetrofitClient.apiInterface.saveUserPrediction(
            userToken, json

        )
        setProgressDialog()
        return callQuestionsSaveAPIService(callApiService)
    }
    fun addWalletAmountList(
        userToken: String,
        json: JsonObject,
    ): MutableLiveData<QuestionSaveRes> {
        val callApiService = RetrofitClient.apiInterface.addWalletAmount(
            userToken, json

        )
        setProgressDialog()
        return callQuestionsSaveAPIService(callApiService)
    }
    fun getMyPredictionList(
        userToken: String,
        page: Int,
        json: JsonObject,
    ): MutableLiveData<MatchesResponse> {
        val callApiService = RetrofitClient.apiInterface.getMyPredictionList(
            userToken,page, json

        )
        setProgressDialog()
        return callMatchAPIService(callApiService)
    }
    fun getMatchOverPredictedDetails(
        userToken: String,
        json: JsonObject,
    ): MutableLiveData<MatchesDetailsRes> {
        val callApiService = RetrofitClient.apiInterface.getMatchPredictedOverDetails(userToken, json)
        setProgressDialog()
        return callMatchDetailsAPIService(callApiService)
    }
    fun getMatchOverPredictedResultDetails(
        userToken: String,
        json: JsonObject,
    ): MutableLiveData<OverResultDetailsRes> {
        val callApiService = RetrofitClient.apiInterface.getMatchPredictedOverResult(
            userToken, json

        )
        setProgressDialog()
        return callMatchOverResultsAPIService(callApiService)
    }
    fun callHowToPlayAPIService(call: Call<HowToPlayResponse>): MutableLiveData<HowToPlayResponse> {
        val modelRes = MutableLiveData<HowToPlayResponse>()
        makeApiCall(call, onSuccess = { data ->
            // Handle successful response
            // data is of type MyData or null
            val model: HowToPlayResponse? = data
            if (model != null) {
                modelRes.value = model!!
            }

        },
            onError = { error ->
                // Handle error
            })

        return modelRes


    }

    fun callMatchAPIService(call: Call<MatchesResponse>): MutableLiveData<MatchesResponse> {
        val modelRes = MutableLiveData<MatchesResponse>()
        makeApiCall(call, onSuccess = { data ->
            // Handle successful response
            // data is of type MyData or null
            val model: MatchesResponse? = data
            if (model != null) {
                modelRes.value = model!!
            }

        },
            onError = { error ->
                // Handle error
            })

        return modelRes


    }

    fun callMatchDetailsAPIService(call: Call<MatchesDetailsRes>): MutableLiveData<MatchesDetailsRes> {
        val modelRes = MutableLiveData<MatchesDetailsRes>()
        makeApiCall(call, onSuccess = { data ->
            // Handle successful response
            // data is of type MyData or null
            val model: MatchesDetailsRes? = data
            if (model != null) {
                modelRes.value = model!!
            }

        },
            onError = { error ->
                // Handle error
            })

        return modelRes


    }
    fun callQuestionsSaveAPIService(call: Call<QuestionSaveRes>): MutableLiveData<QuestionSaveRes> {
        val modelRes = MutableLiveData<QuestionSaveRes>()
        makeApiCall(call, onSuccess = { data ->
            // Handle successful response
            // data is of type MyData or null
            val model: QuestionSaveRes? = data
            if (model != null) {
                modelRes.value = model!!
            }

        },
            onError = { error ->
                // Handle error
            })

        return modelRes


    }
    fun callMatchOverResultsAPIService(call: Call<OverResultDetailsRes>): MutableLiveData<OverResultDetailsRes> {
        val modelRes = MutableLiveData<OverResultDetailsRes>()
        makeApiCall(call, onSuccess = { data ->
            // Handle successful response
            // data is of type MyData or null
            val model: OverResultDetailsRes? = data
            if (model != null) {
                modelRes.value = model!!
            }

        },
            onError = { error ->
                // Handle error
            })

        return modelRes


    }
    fun callQuestionListAPIService(call: Call<QuestionListRes>): MutableLiveData<QuestionListRes> {
        val modelRes = MutableLiveData<QuestionListRes>()
        makeApiCall(call, onSuccess = { data ->
            // Handle successful response
            // data is of type MyData or null
            val model: QuestionListRes? = data
            if (model != null) {
                modelRes.value = model!!
            }

        },
            onError = { error ->
                // Handle error
            })

        return modelRes


    }

    fun <T> makeApiCall(call: Call<T>, onSuccess: (T?) -> Unit, onError: (String) -> Unit) {

        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                progressDialog.dismissDialog()
                if (response.isSuccessful) {
                    onSuccess(response.body())
                } else if (response.code() == 401) {
                    InvalidSession(context, response.message())
                } else {
                    showToast(context, SOMETHING_WENT_WRONG_ERROR)
                    showToast(context, response.message())

                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                progressDialog.dismissDialog()
                showToast(context, t.message.toString())
                Log.e("error", t.message.toString())

            }
        })
    }

    private fun setProgressDialog() {

        progressDialog.showProgressDialog(context.getString(R.string.loading))
    }
}


