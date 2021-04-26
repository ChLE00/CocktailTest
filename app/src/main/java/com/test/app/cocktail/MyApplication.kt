package com.test.app.cocktail

import android.app.Application
import com.test.app.cocktail.utils.SharedPrefaceHelper.Companion.init

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        init(this)
    }
}