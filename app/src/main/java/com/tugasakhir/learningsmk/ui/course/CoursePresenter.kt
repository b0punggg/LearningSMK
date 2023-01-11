package com.tugasakhir.learningsmk.ui.course

import com.tugasakhir.learningsmk.network.ApiService
import kotlinx.coroutines.*

class CoursePresenter (
        private val view: CourseView,
        private val api: ApiService,
) {

    init {
        view.setupListener()
        fetchCategory()
        fetchCourse("")
    }

    fun fetchCourse(keyword: String) {
        view.courseLoading(true)
        GlobalScope.launch {
            val response = api.course( keyword )
            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    view.courseResponse( response.body()!! )
                    view.courseLoading(false)
                }
            } else {
                view.courseError("Terjadi kesalahan")
            }
        }
    }

    fun fetchCategory() {
        view.courseLoading(true)
        GlobalScope.launch {
            val response = api.category()
            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    view.categoryResponse( response.body()!! )
                    view.courseLoading(false)
                }
            } else {
                view.courseError("Terjadi kesalahan")
            }
        }
    }

    fun fetchCourse(categoryId: Int) {
        view.courseLoading(true)
        GlobalScope.launch {
            val response = api.courseByCategory( categoryId )
            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    view.courseResponse( response.body()!! )
                    view.courseLoading(false)
                }
            } else {
                view.courseError("Terjadi kesalahan")
            }
        }
    }

}

interface CourseView {
    fun setupListener()
    fun courseLoading(loading: Boolean)
    fun courseResponse(response: CourseResponse)
    fun categoryResponse(response: CategoryResponse)
    fun courseError(msg: String)
}