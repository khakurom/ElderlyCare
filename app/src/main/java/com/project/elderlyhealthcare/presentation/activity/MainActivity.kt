package com.project.elderlyhealthcare.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.project.elderlyhealthcare.R
import com.project.elderlyhealthcare.databinding.ActivityMainBinding
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
		binding.bottomNav.setupWithNavController(navController)
	}

	override fun updateBottomNavVisible(hide: Boolean) {
		binding.bottomNav.visibility = if (hide) View.GONE else View.VISIBLE
	}
}