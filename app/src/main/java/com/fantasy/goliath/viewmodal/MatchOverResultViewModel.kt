package com.fantasy.goliath.viewmodal

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.fantasy.goliath.model.MatchesDetailsRes
import com.fantasy.goliath.model.OverResultDetailsRes

import com.fantasy.goliath.network.repository.RemoteRepository
import com.google.gson.JsonObject

class MatchOverResultViewModel : ViewModel() {

    fun getMatchesDetails(
        context: Context,
        token: String,
        json: JsonObject
    ): LiveData<MatchesDetailsRes> {
        return RemoteRepository(context).getMatchOverPredictedDetails(token, json)

    }

    fun getOverResultDetails(
        context: Context,
        token: String,
        json: JsonObject
    ): LiveData<OverResultDetailsRes> {
        return RemoteRepository(context).getMatchOverPredictedResultDetails(token, json)

    }
}