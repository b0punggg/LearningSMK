package com.tugasakhir.learningsmk.ui.module

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import com.tugasakhir.learningsmk.R
import com.tugasakhir.learningsmk.databinding.ActivityModuleBinding
import com.tugasakhir.learningsmk.network.ApiClient
import com.tugasakhir.learningsmk.persistence.DatabaseClient
import com.tugasakhir.learningsmk.preferences.PrefManager
import com.tugasakhir.learningsmk.ui.BaseActivity
import com.tugasakhir.learningsmk.ui.course.CourseData
import com.tugasakhir.learningsmk.util.loadImage

class ModuleActivity : BaseActivity(), ModuleView {

    private val binding by lazy { ActivityModuleBinding.inflate( layoutInflater ) }
    private lateinit var presenter: ModulePresenter
    private lateinit var adapter: ModuleAdapter
    private lateinit var course: CourseData
    private val id by lazy { intent.getIntExtra("id", 0 ) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        presenter = ModulePresenter(
            this,
            ApiClient.getService(),
            DatabaseClient.getService(this).courseDao(),
            PrefManager(this)
        )
    }

    override fun onStart() {
        super.onStart()
        if (id != 0) {
            presenter.fetchModule(id)
            presenter.find(id)
        }
    }

    override fun setupListener() {
        adapter = ModuleAdapter(arrayListOf(), object : ModuleAdapter.AdapterListener {
            override fun onClick(detail: DetailData) {
                startActivity(
                    Intent(this@ModuleActivity, DetailActivity::class.java)
                        .putExtra("id", detail.id)
                )
            }
        })
        binding.listModule.adapter = adapter
        binding.imageBookmark.setOnClickListener {
            if (this::course.isInitialized) presenter.addBookmark( course )
        }
        binding.swipe.setOnRefreshListener {
            if (id != 0) {
                presenter.fetchModule(id)
                presenter.find(id)
            }
        }
    }

    override fun moduleLoading(loading: Boolean) {
        binding.swipe.isRefreshing = loading
    }

    override fun moduleResponse(response: ModuleResponse) {

        course = response.data.course
        val module = response.data.detail
        Log.e("module_", module.toString())

        loadImage( binding.imageCover, course.thumbnail )
        binding.textTitle.text = course.title
        binding.textMentor.text = course.guru //guru

        adapter.addList( module )
        binding.textModules.text = "${module.size} Pelajaran"

        var views = 0
        module.forEach {
            views += it.view.toInt()
            binding.textViews.text = "${views}x dilihat"
        }

        binding.buttonLink.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse( course.group )
            startActivity(openURL)
        }
    }

    override fun moduleError(msg: String) {
        binding.swipe.isRefreshing = false
    }

    override fun isBookmark(bookmark: Int) {
        Log.e("isBookmark", "bookmark $bookmark")
        when (bookmark) {
            1 -> binding.imageBookmark.setImageResource( R.drawable.ic_bookmark_added )
            else -> binding.imageBookmark.setImageResource( R.drawable.ic_bookmark_add )
        }
    }
}