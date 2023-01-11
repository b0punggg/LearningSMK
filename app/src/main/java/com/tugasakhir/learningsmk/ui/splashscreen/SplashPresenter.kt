package com.tugasakhir.learningsmk.ui.splashscreen

import com.tugasakhir.learningsmk.preferences.PrefManager

class SplashPresenter(
    private val view: SplashView,
    private val pref: PrefManager
) {

    init {
        view.nextPage( pref.getInt("is_login") )
    }
}

interface SplashView {
    fun nextPage(isLogin: Int)
}