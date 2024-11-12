package com.marossolutions.mychatbotapp

import android.app.Application
import com.marossolutions.mychatbotapp.di.initKoin
import org.koin.android.ext.koin.androidContext

class ChatbotApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@ChatbotApplication)
        }
    }
}
