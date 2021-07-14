package com.stanar.obrtstanar.Klase

import android.app.Application
import android.content.Context

class ObrtStanar : Application() {
    companion object {
        lateinit var ApplicationContext: Context
            private set
    }
    override fun onCreate() {
        super.onCreate()
        ApplicationContext = this
    }
}