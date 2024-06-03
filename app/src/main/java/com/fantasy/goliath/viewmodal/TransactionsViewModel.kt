package com.fantasy.goliath.viewmodal

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.fantasy.goliath.model.LeaderBoardListRes
import com.fantasy.goliath.model.MatchesDetailsRes
import com.fantasy.goliath.model.TransactionListRes

import com.fantasy.goliath.network.repository.RemoteRepository
import com.google.gson.JsonObject

class TransactionsViewModel : ViewModel() {

    fun getTransactionsList(context: Context, token: String,page: Int,   json: JsonObject): LiveData<TransactionListRes> {
        return RemoteRepository(context).getTransactionsList(token,page, json)

    }
}