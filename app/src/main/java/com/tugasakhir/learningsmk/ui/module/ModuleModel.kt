package com.tugasakhir.learningsmk.ui.module

import com.tugasakhir.learningsmk.ui.course.CourseData

data class ModuleResponse(
        val status: Boolean,
        val data: ModuleData
)

data class ModuleData (
        val course: CourseData,
        val detail: List<DetailData>
)

data class DetailData (
        val id: Int,
        val title: String,
        val description: String,
        val module_type: String,
        val file_type: String?,
        val document: String,
        val youtube: String,
        val order: String,
        val view: String,
)

data class DetailResponse (
        val data: DetailData
)