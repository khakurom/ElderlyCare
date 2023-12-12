package com.project.elderlyhealthcare.presentation.activity

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.project.elderlyhealthcare.R
import com.project.elderlyhealthcare.utils.Constant.NOTIFICATION_PERMISSION
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NotLoginActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		accessNotificationPermission()
		setContentView(R.layout.activity_not_login)
	}


	private fun accessNotificationPermission() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
			if (ContextCompat.checkSelfPermission(this, NOTIFICATION_PERMISSION) == PackageManager.PERMISSION_DENIED) {
				requestNotificationPermissionLauncher.launch(NOTIFICATION_PERMISSION)
			}
		}
	}


	@SuppressLint("MissingPermission")
	private val requestNotificationPermissionLauncher = registerForActivityResult(
		ActivityResultContracts.RequestPermission()
	) { granted ->
		if (!granted) {
			accessNotificationPermission()
			Toast.makeText(this, "Vui lòng bật cho phép thông báo", Toast.LENGTH_SHORT).show()
		}
	}
}