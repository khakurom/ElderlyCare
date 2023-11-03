package com.project.elderlyhealthcare.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.project.elderlyhealthcare.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NotLoginActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_not_login)
	}
}