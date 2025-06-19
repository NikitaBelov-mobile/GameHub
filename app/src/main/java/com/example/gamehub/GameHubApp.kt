package com.example.gamehub

import android.app.Application
import com.example.gamehub.di.AppContainer

class GameHubApp : Application() {
    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}
