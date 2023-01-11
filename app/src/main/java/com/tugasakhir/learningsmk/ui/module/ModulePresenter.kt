package com.tugasakhir.learningsmk.ui.module

import com.tugasakhir.learningsmk.network.ApiService
import com.tugasakhir.learningsmk.persistence.Course
import com.tugasakhir.learningsmk.persistence.CourseDao
import com.tugasakhir.learningsmk.preferences.PrefManager
import com.tugasakhir.learningsmk.ui.course.CourseData
import com.tugasakhir.learningsmk.util.userLogin
import kotlinx.coroutines.*

class ModulePresenter (
    private val view: ModuleView,
    private val api: ApiService,
    private val db: CourseDao,
    private val pref: PrefManager,
) {

    init {
        view.setupListener()
    }

    fun fetchModule(id: Int) {
        view.moduleLoading(true)
        GlobalScope.launch {
            val response = api.courseById( id )
            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    view.moduleResponse( response.body()!! )
                    view.moduleLoading(false)
                }
            } else {
                view.moduleError("Terjadi kesalahan")
            }
        }
    }

    fun find(id: Int){
        GlobalScope.launch {
            val bookmark = db.find(id, userLogin(pref).id)
            withContext(Dispatchers.Main) {
                view.isBookmark( bookmark )
            }
        }
    }

    fun addBookmark(data: CourseData) {
        val course = Course (
                id = data.id,
                user_id = userLogin(pref).id,
                thumbnail = data.thumbnail,
                title = data.title,
                guru = data.guru //guru
        )
        GlobalScope.launch {
            db.add(course)
            withContext(Dispatchers.Main) {
                view.isBookmark( 1 )
            }
        }
    }

}

interface ModuleView {
    fun setupListener()
    fun moduleLoading(loading: Boolean)
    fun moduleResponse(response: ModuleResponse)
    fun moduleError(msg: String)
    fun isBookmark( bookmark: Int )
}