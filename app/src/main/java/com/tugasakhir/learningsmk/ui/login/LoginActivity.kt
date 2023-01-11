package com.tugasakhir.learningsmk.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.tugasakhir.learningsmk.ui.BaseActivity
import com.tugasakhir.learningsmk.ui.home.HomeActivity
import com.tugasakhir.learningsmk.databinding.ActivityLoginBinding
import com.tugasakhir.learningsmk.preferences.PrefManager
import com.tugasakhir.learningsmk.network.ApiClient

class LoginActivity : BaseActivity(), LoginView {

    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    private lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        presenter = LoginPresenter (
                this,
                PrefManager(this),
                ApiClient.getService()
        )
    }

    override fun setupListener() {

        binding.buttonLogin.setOnClickListener {
            presenter.fetchLogin(
                    binding.editEmail.text.toString(),
                    binding.editPassword.text.toString()
            )
        }
    }

    override fun loginLoading(loading: Boolean) {
        binding.buttonLogin.isEnabled = loading.not()
        when (loading ) {
            true -> binding.buttonLogin.text = "Tunggu.."
            false -> binding.buttonLogin.text = "Masuk"
        }
    }

    override fun loginResponse( response: LoginResponse ) {
        if (response.status) {
            Toast.makeText(applicationContext, response.msg, Toast.LENGTH_SHORT).show()
            presenter.saveSession( response.data!!, binding.editPassword.text.toString() )
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        } else {
            Toast.makeText(applicationContext, response.msg, Toast.LENGTH_SHORT).show()
        }
    }

    override fun loginError(msg: String) {
        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
        loginLoading(false)
    }
}