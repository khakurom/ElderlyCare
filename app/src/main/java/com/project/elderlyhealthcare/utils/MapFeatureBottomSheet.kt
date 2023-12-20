package com.project.elderlyhealthcare.utils

import android.content.Context
import android.os.Bundle
import android.widget.Button
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.project.elderlyhealthcare.R

class MapFeatureBottomSheet (context: Context, private val onBottomSheetClickListener: OnBottomSheetClickListener) :
    BottomSheetDialog(context) {

    interface OnBottomSheetClickListener {
        fun onViewElderLocation ()
        fun onTrackToElderLocation ()
        fun onViewMyLocation ()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bottom_sheet_feature_map)

        val btView = findViewById<Button>(R.id.bottom_sheet_bt_view_elder_location)
        val btViewMyLocation  = findViewById<Button>(R.id.bottom_sheet_bt_view_my_location)
        val btTrack = findViewById<Button>(R.id.bottom_sheet_bt_track_to_elder_location)

        btViewMyLocation?.setOnClickListener {
            onBottomSheetClickListener.onViewMyLocation( )
            dismiss()
        }

        btView?.setOnClickListener {
            onBottomSheetClickListener.onViewElderLocation( )
            dismiss()
        }

        btTrack?.setOnClickListener {
            onBottomSheetClickListener.onTrackToElderLocation( )
            dismiss()
        }
    }
}