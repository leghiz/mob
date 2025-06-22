package ru.fefu.helloworld

import android.app.Application
import androidx.room.Room

class App : Application() {
    companion object {
        var currentUsername: String = "user"
        lateinit var database: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "activity-database"
        ).build()
    }
}