package com.tugasakhir.learningsmk.ui.profile

import android.util.Log
import com.google.gson.Gson
import com.tugasakhir.learningsmk.network.ApiService
import com.tugasakhir.learningsmk.persistence.CourseDao
import com.tugasakhir.learningsmk.preferences.PrefManager
import com.tugasakhir.learningsmk.ui.login.LoginData
import com.tugasakhir.learningsmk.ui.login.LoginResponse
import com.tugasakhir.learningsmk.util.userLogin
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class ProfilePresenter (
        private val view: ProfileView,
        private val pref: PrefManager,
        private val db: CourseDao,
        private val api: ApiService,
) {

    init {
        view.setupListener()
//        view.user( user() )
        view.user( userLogin( pref ) )
    }

    fun logout(){
        pref.clear()
        view.logout()
    }

    private fun user() : LoginData {
        val json = pref.getString( "user_login")
        return Gson().fromJson(json, LoginData::class.java )
    }

    fun count() {
        GlobalScope.launch {
            val count = db.count( userId = userLogin(pref).id )
            withContext(Dispatchers.Main) {
                view.courseCount( count )
            }
        }
    }

    fun uploadAvatar(avatar: File, id: Int){

        val requestBody: RequestBody = avatar.asRequestBody("image/*".toMediaTypeOrNull())
        val multipartBody: MultipartBody.Part =
            MultipartBody.Part.createFormData("avatar", avatar.name, requestBody)

        view.uploadLoading(true)
        GlobalScope.launch {
            val response = api.avatar( multipartBody, id)
            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    view.uploadResponse( response.body()!! )
                    view.uploadLoading(false)
                }
            } else {
                view.uploadError("Terjadi kesalahan")
            }
        }
    }

    fun reLogin(email: String) {
        GlobalScope.launch {
            val response = api.login(email, pref.getString("user_password")!!)
            Log.d("user_res", "" + response.body())
            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    view.loginResponse( response.body()!! )
                }
            }
        }
    }

    fun updateSession(data: LoginData){
        pref.put( "user_login", Gson().toJson( data ) )
    }
}

interface ProfileView {
    fun setupListener()
    fun user(user: LoginData)
    fun courseCount(count: Int)
    fun uploadLoading(loading: Boolean)
    fun uploadResponse(avatar: AvatarResponse)
    fun uploadError(message: String)
    fun loginResponse(login: LoginResponse)
    fun logout()
}