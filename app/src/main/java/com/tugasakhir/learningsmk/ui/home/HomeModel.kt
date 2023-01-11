package com.tugasakhir.learningsmk.ui.home

import com.tugasakhir.learningsmk.ui.course.CourseData

data class HomeResponse(
        val data: HomeData
)

data class HomeData(
        val latest: List<CourseData>,
        val popular: List<CourseData>,
)