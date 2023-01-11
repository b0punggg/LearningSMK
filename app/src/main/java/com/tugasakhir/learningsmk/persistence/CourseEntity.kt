package com.tugasakhir.learningsmk.persistence

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tableCourse")
data class Course (
        @PrimaryKey(autoGenerate = false)
        val id: Int,
        val user_id: Int,
        val thumbnail: String,
        val title: String,
        val guru: String,
)