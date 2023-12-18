package com.project.elderlyhealthcare.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MedicineEventModel(
    val id : Int,
    val hour : String? = null,
    val minute : String? = null,
    val dayBegin : String,
    val dayEnd : String,
    val medicineName : List <String>,
    val medicineDose : List <Int>,
    val diseaseName : String,
    val uniqueIntent : Int,
    val isOn : Boolean? = null
) : Parcelable
