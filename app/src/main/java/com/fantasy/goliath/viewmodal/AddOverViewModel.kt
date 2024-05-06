package com.fantasy.goliath.viewmodal

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.fantasy.goliath.model.MatchesDetailsRes

import com.fantasy.goliath.network.repository.RemoteRepository
import com.google.gson.JsonObject

class AddOverViewModel : ViewModel() {

    fun getMatchesDetails(context: Context, token: String,   json: JsonObject): LiveData<MatchesDetailsRes> {
        return RemoteRepository(context).getMatchDetails(token, json)

    }
}