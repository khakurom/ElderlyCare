package com.project.elderlyhealthcare.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MedicineEventModel(
    val id : Int,
    val hour : String? = null,
    val minutes : String? = null,
    val dayRepeat : List <String?> ,
    val dayBegin : String,
    val dayEnd : String,
    val medicineName : List <String>,
    val medicineDose : List <Int>,
    val diseaseName : String
) : Parcelable