package com.fantasy.goliath.viewmodal

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.fantasy.goliath.model.MatchesDetailsRes
import com.fantasy.goliath.model.QuestionListRes
import com.fantasy.goliath.model.QuestionSaveRes

import com.fantasy.goliath.network.repository.RemoteRepository
import com.google.gson.JsonObject

class WalletViewModel : ViewModel() {


    fun addWalletAmountList(context: Context, token: String,   json: JsonObject): LiveData<QuestionSaveRes> {
        return RemoteRepository(context).addWalletAmountList(token, json)

    }
}