package com.fantasy.goliath.viewmodal

import android.content.Context
import androidx.lifecycle.LiveData

import androidx.lifecycle.ViewModel
import com.fantasy.goliath.model.HowToPlayResponse
import com.fantasy.goliath.model.MatchesResponse
import com.fantasy.goliath.network.repository.RemoteRepository
import com.google.gson.JsonObject

class GameGuideViewModel : ViewModel() {

    fun getGuideList(context: Context, token: String): LiveData<HowToPlayResponse> {
        return RemoteRepository(context).getHowToPlayList(token )

    }
}