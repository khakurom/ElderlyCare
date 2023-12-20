package com.project.elderlyhealthcare.presentation.fragment.main.location

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Location
import android.location.LocationManager
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.project.elderlyhealthcare.BR
import com.project.elderlyhealthcare.R
import com.project.elderlyhealthcare.databinding.FragmentLocationBinding
import com.project.elderlyhealthcare.domain.models.CustomerInfoModel
import com.project.elderlyhealthcare.domain.models.LocationModel
import com.project.elderlyhealthcare.presentation.fragment.base.BaseFragment
import com.project.elderlyhealthcare.presentation.viewmodels.main.LocationViewModel
import com.project.elderlyhealthcare.utils.Constant
import com.project.elderlyhealthcare.utils.DelegatedPreferences
import com.project.elderlyhealthcare.utils.MapFeatureBottomSheet
import com.project.elderlyhealthcare.utils.OnFragmentInteractionListener
import com.project.elderlyhealthcare.utils.SingleClickListener
import com.project.elderlyhealthcare.utils.Utils
import com.project.elderlyhealthcare.utils.Utils.showDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationFragment : BaseFragment<LocationViewModel, FragmentLocationBinding>(R.layout.fragment_location), OnMapReadyCallback,
    GoogleMap.OnMyLocationClickListener {

    private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference
    private lateinit var mMap: GoogleMap

    private var listener: OnFragmentInteractionListener? = null

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun variableId(): Int = BR.locationViewModel

    override fun createViewModel(): Lazy<LocationViewModel> = activityViewModels()

    override fun bindView(view: View): FragmentLocationBinding {
        return FragmentLocationBinding.bind(view)
    }

    override fun init() {
        super.init()
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        binding.apply {
            locationFrCsBar.customAppBarIvBack.setOnClickListener(object : SingleClickListener() {
                override fun onSingleClick(v: View) {
                    backToPreScreen()
                }
            })
            locationBtOpenFeature.setOnClickListener(object : SingleClickListener() {
                override fun onSingleClick(v: View) {
                    openMapFeatures()
                }
            })
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        listener?.updateBottomNavVisible(true)
    }

    private fun openMapFeatures() {
        val customBottomSheet = MapFeatureBottomSheet(
            requireContext(),
            object : MapFeatureBottomSheet.OnBottomSheetClickListener {
                override fun onViewElderLocation() {
                    checkNetworkIsAvailable ()
                }

                override fun onTrackToElderLocation() {

                }

                override fun onViewMyLocation() {
                    requestEnableLocation (requireContext())
                }
            })
        customBottomSheet.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        customBottomSheet.show()
    }




    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.apply {
            isMyLocationEnabled = true
            setOnMyLocationClickListener(this@LocationFragment)
            uiSettings.apply {
                isMyLocationButtonEnabled = true
            }
        }
        requestEnableLocation(requireContext())
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.let {
                    // Move camera to the current location with animation
                    val currentLatLng = LatLng(it.latitude, it.longitude)
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f), 1500, null)
                }
            }
    }


    private fun checkNetworkIsAvailable () {
        if (Utils.isNetworkAvailable(requireContext())) {
            viewElderLocation ()
        } else {
            showDialog(requireContext(), "Mất kết nối")
        }
    }


    private fun viewElderLocation () {
        getElderLocationFirebase {locationModel ->
            locationModel?.let {
                if (it.lat != null && it.lng !=null){
                    val currentLatLng = LatLng(it.lat, it.lng)
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f), 1500, null)
                    mMap.addMarker(MarkerOptions().position(currentLatLng))
                }
            }
        }
    }

    private fun getElderLocationFirebase (callback: (LocationModel?) -> Unit) {
        binding.progressBar.visibility = View.VISIBLE
        val phoneNumber = DelegatedPreferences(requireContext(), Constant.PHONE_NUMBER, "").getValue()
        val dataNodeReference = databaseReference.child("data").child(phoneNumber).child(getString(R.string.key_location))

        dataNodeReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val locationModel = dataSnapshot.getValue(LocationModel::class.java)
                    callback(locationModel)
                } else {
                    callback(null)
                }
                binding.progressBar.visibility = View.GONE
            }

            override fun onCancelled(databaseError: DatabaseError) {
                binding.progressBar.visibility = View.GONE
                showDialog (requireContext(),"Kết nối không ổn định")
                callback(null)
            }
        })
    }

    fun requestEnableLocation(context: Context) {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            AlertDialog.Builder(context)
                .setTitle("Bật định vị của thiết bị")
                .setMessage("Để xem vị trí một cách chính xác, bạn cần phải bật định vị của thiết bị")
                .setPositiveButton("Đồng ý") { _, _ ->
                    context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                }
                .setNegativeButton("Không") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        } else {
            getCurrentLocation()
        }
    }


    override fun onMyLocationClick(p0: Location) {

    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        listener?.updateBottomNavVisible(false)
    }


}