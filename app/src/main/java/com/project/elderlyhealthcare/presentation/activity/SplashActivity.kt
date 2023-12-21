package com.project.elderlyhealthcare.presentation.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.project.elderlyhealthcare.R
import com.project.elderlyhealthcare.utils.authenticate.UserManager

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSplashScreen()
    }


    private fun setSplashScreen() {
        val loginFlag = UserManager.getInstance(this).isLogged()
        val uri = intent.data
        Log.d("khatag", uri.toString())
        Handler(Looper.getMainLooper()).postDelayed({
            if (loginFlag) {
                startActivity(Intent(this, MainActivity::class.java))

            } else {
                startActivity(Intent(this, NotLoginActivity::class.java))
            }
            finish()
        }, 2000L)


    }
}