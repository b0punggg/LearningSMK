package com.tugasakhir.learningsmk.ui.course

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import com.tugasakhir.learningsmk.databinding.ActivityCourseBinding
import com.tugasakhir.learningsmk.network.ApiClient
import com.tugasakhir.learningsmk.ui.BaseActivity
import com.tugasakhir.learningsmk.ui.module.ModuleActivity

class CourseActivity : BaseActivity(), CourseView {

    private val binding by lazy { ActivityCourseBinding.inflate(layoutInflater) }
    private lateinit var presenter: CoursePresenter
    private lateinit var courseAdapter: CourseAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private var keyword = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        presenter = CoursePresenter(this, ApiClient.getService())
    }

    override fun setupListener() {
        courseAdapter = CourseAdapter(arrayListOf(), object: CourseAdapter.AdapterListener {
            override fun onClick(course: CourseData) {
                startActivity(
                    Intent(this@CourseActivity, ModuleActivity::class.java)
                        .putExtra("id", course.id)
                )
            }
        })
        binding.listCourse.adapter = courseAdapter

        categoryAdapter = CategoryAdapter(arrayListOf(), object: CategoryAdapter.AdapterListener {
            override fun onClick(category: CategoryData) {
                if (category.id == 0 ) presenter.fetchCourse( keyword )
                else presenter.fetchCourse( category.id )
            }
        })
        binding.listCategory.adapter = categoryAdapter

        binding.editSearch.doAfterTextChanged {
            keyword = it.toString()
        }
        binding.editSearch.setOnEditorActionListener { textView, id, keyEvent ->
            if (id == EditorInfo.IME_ACTION_SEARCH) {
                presenter.fetchCourse(keyword)
            }
            false
        }

        binding.swipe.setOnRefreshListener {
            presenter.fetchCourse(keyword)
        }
    }

    override fun courseLoading(loading: Boolean) {
        binding.swipe.isRefreshing = loading
    }

    override fun courseResponse(response: CourseResponse) {
        courseAdapter.addList( response.data )
    }

    override fun categoryResponse(response: CategoryResponse) {
        categoryAdapter.addList( response.data )
    }

    override fun courseError(msg: String) {
        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
        binding.swipe.isRefreshing = false
    }
}