package com.fantasy.goliath.viewmodal

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.fantasy.goliath.model.LeaderBoardListRes
import com.fantasy.goliath.model.MatchesDetailsRes

import com.fantasy.goliath.network.repository.RemoteRepository
import com.google.gson.JsonObject

class LeaderBoardViewModel : ViewModel() {

    fun getLeaderboardList(context: Context, token: String,   json: JsonObject): LiveData<LeaderBoardListRes> {
        return RemoteRepository(context).getLeaderboardList(token, json)

    }
}