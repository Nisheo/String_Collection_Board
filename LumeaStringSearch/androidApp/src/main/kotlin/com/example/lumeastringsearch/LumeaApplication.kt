package com.example.lumeastringsearch

import android.app.Application
import com.example.lumeastringsearch.di.initKoin

class LumeaApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin(this)
    }
}

