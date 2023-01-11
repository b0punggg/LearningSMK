package com.tugasakhir.learningsmk.util

import com.google.gson.Gson
import com.tugasakhir.learningsmk.preferences.PrefManager
import com.tugasakhir.learningsmk.ui.login.LoginData

fun userLogin (pref: PrefManager) : LoginData {
    val json = pref.getString( "user_login")
    return Gson().fromJson(json, LoginData::class.java )
}