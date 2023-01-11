package com.tugasakhir.learningsmk.ui.bookmark

import com.tugasakhir.learningsmk.persistence.Course
import com.tugasakhir.learningsmk.persistence.CourseDao
import com.tugasakhir.learningsmk.preferences.PrefManager
import com.tugasakhir.learningsmk.util.userLogin
import kotlinx.coroutines.*

class BookmarkPresenter (
    private val view: BookmarkView,
    private val db: CourseDao,
    private val pref: PrefManager,
) {

    val listBookmark = db.findAll(userId = userLogin(pref).id)

    fun remove(course: Course) {
        GlobalScope.launch {
            db.remove(course)
        }
    }
}

interface BookmarkView {
    fun setupListener()
}