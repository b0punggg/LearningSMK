package com.tugasakhir.learningsmk.ui.module

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.text.HtmlCompat
import com.tugasakhir.learningsmk.databinding.ActivityDetailBinding
import com.tugasakhir.learningsmk.network.ApiClient
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

/** SAMPLE VIDEO "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4" */


class DetailActivity : AppCompatActivity(), DetailView {

    private val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }
    private lateinit var presenter: DetailPresenter
    private val id by lazy { intent.getIntExtra("id", 0) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        presenter = DetailPresenter(this, ApiClient.getService())
    }

    override fun onStart() {
        super.onStart()
        if (id != 0) presenter.fetchDetail( id )
    }

    override fun setupListener() {
        binding.swipe.setOnRefreshListener {
            presenter.fetchDetail(id)
        }
    }

    override fun detailLoading(loading: Boolean) {
        binding.swipe.isRefreshing = loading
    }

    override fun detailResponse(response: DetailResponse) {
        val detail = response.data
        binding.textTitle.text = detail.title
        binding.textDescription.text = HtmlCompat.fromHtml( detail.description , 0)
        when (detail.module_type) {
            "file" -> {
                when (detail.file_type) {
                    "pdf" -> {
                        binding.buttonDownload.apply {
                            visibility = View.VISIBLE
                            setOnClickListener {
                                val openURL = Intent(Intent.ACTION_VIEW)
                                openURL.data = Uri.parse( detail.document )
                                startActivity(openURL)
                            }
                        }
                    }
                    "mp4" -> {
                        binding.videoFile.apply {
                            visibility = View.VISIBLE
                            setUp(
                                    detail.document,
                                    detail.title
                            )
                        }
                    }
                }
            }
            "youtube" -> {
                lifecycle.addObserver(binding.videoYoutube)
                binding.videoYoutube.apply {
                    visibility = View.VISIBLE
                    addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            super.onReady(youTubePlayer)
                            youTubePlayer.loadVideo( detail.youtube , 0F)
                        }
                    })
                }
            }
        }
    }

    override fun detailError(msg: String) {
        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
        binding.swipe.isRefreshing = false
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("DetailActivity", "onDestroy")
        binding.videoFile.apply {
            if (visibility == View.VISIBLE) setUp("", "")
        }
    }
}