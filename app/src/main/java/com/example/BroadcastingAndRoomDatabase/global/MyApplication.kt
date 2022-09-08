package com.example.BroadcastingAndRoomDatabase.global

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RoomDatabase : Application() {

    override fun onCreate() {
        super.onCreate()
    }

}