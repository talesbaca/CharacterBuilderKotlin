package com.example.myapplication

import android.app.Application
import androidx.room.Room
import com.example.myapplication.persistence.AppDatabase

class MyApp : Application() {
    lateinit var database: AppDatabase

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "character-database"
        ).build()
    }
}