package com.project.elderlyhealthcare.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class ReExaminationEventModel(
    val id : Int,
    val hour : String,
    val minute : String,
    val dayBegin : String,
    val diseaseName : String,
    val address : String? = null,
    val uniqueIntent : Int,
    val isOn : Boolean

) : Parcelable
