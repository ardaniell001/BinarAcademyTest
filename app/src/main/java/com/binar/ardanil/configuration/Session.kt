package com.binar.ardanil.configuration

import android.content.Context
import android.content.SharedPreferences
import com.binar.ardanil.model.Profile
import com.google.gson.Gson

class Session(context: Context) {

    private var prefUser: SharedPreferences? = null
    private var sessionProfile: SharedPreferences.Editor? = null
    private val PREF_USER = "Login"
    private val IS_PROFILE_EXIST = "isProfileExist"
    private val USER = "User"

    init {
        prefUser = context.getSharedPreferences(PREF_USER, 0)
        sessionProfile = prefUser?.edit()
    }

    fun setUser(profile: Profile): Boolean {
        sessionProfile?.putBoolean(IS_PROFILE_EXIST, true)
        sessionProfile?.putString(USER, Gson().toJson(profile))
        sessionProfile?.commit()
        return true
    }

    fun isProfileExist(): Boolean {
        return prefUser?.getBoolean(IS_PROFILE_EXIST, false)!!
    }

    fun getUser(): Profile {
        return Gson().fromJson(prefUser?.getString(USER, null), Profile::class.java)
    }
}