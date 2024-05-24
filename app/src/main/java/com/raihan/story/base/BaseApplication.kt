package com.raihan.story.base

import android.app.Application
import com.raihan.story.utils.koinModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@BaseApplication)
            modules(koinModules)
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        stopKoin()
    }
}