package com.project.elderlyhealthcare.presentation.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.project.elderlyhealthcare.R
import com.project.elderlyhealthcare.databinding.ActivityMainBinding
import com.project.elderlyhealthcare.domain.models.ExerciseEventModel
import com.project.elderlyhealthcare.presentation.fragment.main.event.EventFragmentDirections
import com.project.elderlyhealthcare.presentation.fragment.main.overall.OverallFragmentDirections
import com.project.elderlyhealthcare.utils.Constant
import com.project.elderlyhealthcare.utils.OnFragmentInteractionListener
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnFragmentInteractionListener {
	private lateinit var binding: ActivityMainBinding
	private lateinit var navController: NavController


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)
		val navHostFragment = supportFragmentManager.findFragmentById(
			R.id.nav_host_container
		) as NavHostFragment
		navController = navHostFragment.navController
		checkModeNotification()
	}

	private fun checkModeNotification() {
		binding.bottomNav.setupWithNavController(navController)
		when (this.intent?.getBooleanExtra(Constant.KEY_NOTIFICATION,false)) {
			true -> {
				navController.navigate(OverallFragmentDirections.actionOverallFragmentToEventFragment())
				navController.navigate(EventFragmentDirections.actionEventFragmentToDisplayExerciseNotificationFragment(getDataItemEvent(this.intent) as ExerciseEventModel?))
				this@MainActivity.intent?.removeExtra(Constant.KEY_NOTIFICATION)
				binding.bottomNav.selectedItemId = R.id.nav_event
			}
			else -> binding.bottomNav.setupWithNavController(navController)
		}
	}

	override fun updateBottomNavVisible(hide: Boolean) {
		binding.bottomNav.visibility = if (hide) View.GONE else View.VISIBLE
	}

	private fun getDataItemEvent(intent: Intent?): Any? {
		return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
			intent?.getParcelableExtra(Constant.KEY_EXERCISE_EVENT, Any::class.java)
		} else {
			intent?.getParcelableExtra(Constant.KEY_EXERCISE_EVENT)
		}
	}
}