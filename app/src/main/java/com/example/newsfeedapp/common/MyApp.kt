package com.example.newsfeedapp.common

import android.app.Application
import com.example.newsfeedapp.di.*
import dagger.hilt.android.HiltAndroidApp
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@HiltAndroidApp
class MyApp : Application() {

    /*override fun onCreate() {
        super.onCreate()
        //setting up koin
        startKoin {
            androidContext(applicationContext)
            modules(listOf(roomModule, glideModule, networkModule, viewModelModule, apiServiceModule, repoModule))
        }
    }

     */
}