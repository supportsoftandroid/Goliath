package com.fantasy.goliath.utility


import android.content.Context
import android.content.SharedPreferences
import com.fantasy.goliath.model.LoginResponse
import com.fantasy.goliath.utility.Constants.KEY_ACCESS_TOKEN
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class PreferenceManager(context: Context) {
    private val editor: SharedPreferences.Editor


    private var sharedPreferences: SharedPreferences
    fun initPreferenceManager(context: Context): SharedPreferences {
        sharedPreferences = context.getSharedPreferences(
            Constants.SHARED_PREFERENCE_FILE_NAME,
            Context.MODE_PRIVATE
        )
        return sharedPreferences
    }

    fun getLoginData(): LoginResponse? {
        val json: String = sharedPreferences.getString(Constants.KEY_LOGIN_DATA, null).toString()
        val gson = Gson()
        val type: Type = object : TypeToken<LoginResponse>() {}.getType()
        val beans: LoginResponse = try {
            gson.fromJson(json, type)
        } catch (e: Exception) {
            return null
        }
        return beans
    }

    fun setLoginData(beans: LoginResponse?) {
        val gson = Gson()
        val json: String = gson.toJson(beans)

        editor.putString(Constants.KEY_LOGIN_DATA, json)
        editor.apply()
    }


    fun saveString(key: String?, value: String?) {
        editor.putString(key, value)
        editor.apply()
    }

    fun saveAuthToken(value: String?) {
        editor.putString(KEY_ACCESS_TOKEN, value)
        editor.apply()
    }

    fun getAuthToken(): String {
        return sharedPreferences.getString(KEY_ACCESS_TOKEN, "").toString()
    }

    fun getString(key: String): String {
        return sharedPreferences.getString(key, "").toString()
    }

    fun saveBoolean(key: String?, value: Boolean) {
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getBoolean(key: String?): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    fun clearPreferences() {
        editor.clear()
        editor.apply()
    }

    fun saveInt(keyTrainerId: String?, trainerId: Int?) {
        editor.putInt(keyTrainerId, trainerId!!)
        editor.apply()
    }

    init {
        sharedPreferences = initPreferenceManager(context)
        editor = sharedPreferences.edit()
        editor.apply()


//
//        sharedPreferences = context.getSharedPreferences(
//            Constants.SHARED_PREFERENCE_FILE_NAME,
//            Context.MODE_PRIVATE
//        )
//
//
//        editor = sharedPreferences.edit()
//        editor.apply()
    }


}