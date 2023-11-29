package com.project.elderlyhealthcare.utils

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.project.elderlyhealthcare.R

class CustomBottomSheet(context: Context, private val onBottomSheetClickListener: OnBottomSheetClickListener) :
    BottomSheetDialog(context) {

    interface OnBottomSheetClickListener {
        fun onPositiveButtonClick(medicineName: String, medicineDose : String)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bottom_sheet_add_medicine_type)

        val btAdd = findViewById<Button>(R.id.bottom_sheet_bt_add_medicine)
        val edMedicineName = findViewById<EditText>(R.id.bottom_sheet_ed_medicine_name)
        val edMedicineDose = findViewById<EditText>(R.id.bottom_sheet_ed_medicine_dose)

        btAdd?.setOnClickListener {
            val medicineName = edMedicineName?.text.toString().trim()
            val medicineDose = edMedicineDose?.text.toString().trim()

            if (medicineName.isEmpty() || medicineDose.isEmpty()) {
                Toast.makeText(context, "Hãy điền vào các ô còn trống", Toast.LENGTH_SHORT).show()
            } else {
                onBottomSheetClickListener.onPositiveButtonClick(medicineName, medicineDose)
                dismiss()
            }

        }
    }
}