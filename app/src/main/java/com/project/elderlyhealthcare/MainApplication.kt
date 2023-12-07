package com.project.elderlyhealthcare

import android.app.Application
import com.google.firebase.FirebaseApp
import com.project.elderlyhealthcare.utils.authenticate.UserManager
import dagger.hilt.android.HiltAndroidApp

var application: MainApplication? = null

@HiltAndroidApp
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        UserManager.init(applicationContext)
        FirebaseApp.initializeApp(this)
        application = this
    }
}