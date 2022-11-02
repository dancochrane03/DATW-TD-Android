package com.app.datwdt.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.app.datwdt.constants.Constants
import com.app.datwdt.data.model.auth.UserResponse
import com.google.gson.Gson
import com.google.gson.GsonBuilder

class Preferences(context: Context?) {
    public val sharedPreferences: SharedPreferences
    public val editor: SharedPreferences.Editor
    fun getInt(key: String?): Int {
        return sharedPreferences.getInt(key, 0)
    }

    fun putInt(key: String?, value: Int?) {
        editor.putInt(key, value!!)
        editor.commit()
    }

    fun getString(key: String?): String? {
        return sharedPreferences.getString(key, "")
    }

    fun putString(key: String?, value: String?) {
        editor.putString(key, value)
        editor.commit()
    }

    fun getBoolean(key: String?): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    fun putBoolean(key: String?, value: Boolean) {
        editor.putBoolean(key, value)
        editor.commit()
    }

    fun remove(key: String?) {
        editor.remove(key)
        editor.commit()
    }

    fun storeUserDetails(response: UserResponse) {
        putString(Constants.access_token, response.accessToken)
        putString(Constants.entity_id,response.userInfo?.entityId)
        putString(Constants.user_gson, Gson().toJson(response.userInfo))
    }

    fun getUserResponse(): UserResponse.UserInfo? {
        return GsonBuilder().create().fromJson(
            getString(Constants.user_gson),
            UserResponse.UserInfo::class.java
        )
    }

    fun clear() {

        editor.clear().apply()

    }
    init {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        editor = sharedPreferences.edit()
    }
}