package com.fantasy.goliath.viewmodal

import android.content.Context
import androidx.lifecycle.LiveData

import androidx.lifecycle.ViewModel
import com.fantasy.goliath.model.MatchesResponse
import com.fantasy.goliath.network.repository.RemoteRepository
import com.google.gson.JsonObject

class HomeViewModel : ViewModel() {

    fun getMatchesList(context: Context, token: String, currentPage: Int, json: JsonObject): LiveData<MatchesResponse> {
        return RemoteRepository(context).getMatchesList(token,currentPage,json)

    }
}