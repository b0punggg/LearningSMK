package com.tugasakhir.learningsmk.persistence

import android.content.Context
import androidx.room.Room

private const val dbName = "newlearnsmk.db"
//gantidb

object DatabaseClient{
    fun getService( context: Context ): DatabaseService {
        return Room.databaseBuilder(
            context,
            DatabaseService::class.java,
            dbName
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}