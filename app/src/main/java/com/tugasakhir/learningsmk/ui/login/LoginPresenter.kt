package com.tugasakhir.learningsmk.ui.login

import com.google.gson.Gson
import com.tugasakhir.learningsmk.preferences.PrefManager
import com.tugasakhir.learningsmk.network.ApiService
import kotlinx.coroutines.*

class LoginPresenter (
        private val view: LoginView,
        private val pref: PrefManager,
        private val api: ApiService,
) {

    init {
        view.setupListener()
    }

    fun fetchLogin(email: String, password: String) {
        view.loginLoading(true)
        GlobalScope.launch {
            val response = api.login(email, password)
            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    view.loginResponse( response.body()!! )
                    view.loginLoading(false)
                }
            } else {
                view.loginError("Terjadi kesalahan")
            }
        }
    }

    fun saveSession(data: LoginData, password: String){
        pref.put("is_login", 1)
        pref.put( "user_login", Gson().toJson( data ) )
        pref.put( "user_password", password )
    }

}

interface LoginView {
    fun setupListener()
    fun loginLoading(loading: Boolean)
    fun loginResponse(response: LoginResponse)
    fun loginError(msg: String)
}