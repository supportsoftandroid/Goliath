package com.fantasy.goliath.viewmodal


import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.fantasy.goliath.model.AppContentRes
import com.fantasy.goliath.network.repository.RemoteRepository
import com.google.gson.JsonObject


class StaticViewModal : ViewModel() {



     fun getAppContent(context: Context , type: String ): LiveData<AppContentRes> {
         val json = JsonObject()
         json.addProperty("key",type)
         return RemoteRepository(context).getAppContent(json )

    }
    fun contactHelp(context: Context , name: String,email: String, message: String ): LiveData<AppContentRes> {
         val json = JsonObject()
         json.addProperty("name",name)
         json.addProperty("email",email)
         json.addProperty("message",message)
         return RemoteRepository(context).getAppContent(json )

    }


}