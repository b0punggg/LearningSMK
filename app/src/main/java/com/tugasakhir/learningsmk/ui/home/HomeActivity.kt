package com.tugasakhir.learningsmk.ui.home

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.tugasakhir.learningsmk.R
import com.tugasakhir.learningsmk.databinding.ActivityHomeBinding
import com.tugasakhir.learningsmk.ui.BaseActivity

class HomeActivity : BaseActivity() {
    private val binding by lazy { ActivityHomeBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.navView.setupWithNavController(
            findNavController(R.id.nav_host_fragment)
        )
    }
}