package com.project.elderlyhealthcare.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class ReExaminationEventModel(
    val id : Int,
    val hour : String? = null,
    val minutes : String? = null,
    val dayBegin : String? = null,
    val diseaseName : String? = null,
    val address : String? = null

) : Parcelable
