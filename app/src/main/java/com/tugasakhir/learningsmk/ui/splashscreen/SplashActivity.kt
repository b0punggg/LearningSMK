package com.tugasakhir.learningsmk.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.tugasakhir.learningsmk.ui.BaseActivity
import com.tugasakhir.learningsmk.R
import com.tugasakhir.learningsmk.preferences.PrefManager
import com.tugasakhir.learningsmk.ui.home.HomeActivity
import com.tugasakhir.learningsmk.ui.login.LoginActivity

class SplashActivity : BaseActivity(), SplashView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        SplashPresenter(this, PrefManager(this))
    }

    override fun nextPage(isLogin: Int) {
        Handler(Looper.myLooper()!!).postDelayed({
            when (isLogin) {
                1 ->  startActivity(Intent(this, HomeActivity::class.java))
                else -> startActivity(Intent(this, LoginActivity::class.java))
            }
            finish()
        }, 1500)
    }
}