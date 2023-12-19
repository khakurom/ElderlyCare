package com.project.elderlyhealthcare.presentation.fragment.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.project.elderlyhealthcare.R
import dagger.hilt.android.AndroidEntryPoint
import hilt_aggregated_deps._com_project_elderlyhealthcare_presentation_activity_NotLoginActivity_GeneratedInjector

@AndroidEntryPoint
class LocationFragment : Fragment() , OnMapReadyCallback{
	private lateinit var mMap: GoogleMap

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		val view = inflater.inflate(R.layout.fragment_location, container, false)

		// Obtain the SupportMapFragment and get notified when the map is ready to be used.
		val mapFragment =
			childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
		mapFragment.getMapAsync(this)
		return view
	}

	override fun onMapReady(googleMap: GoogleMap) {
		mMap = googleMap
		val hoChiMinhCity = LatLng(10.7769, 106.7009)
		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hoChiMinhCity, 12f))
	}


}