package com.project.elderlyhealthcare.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class ExerciseEventModel(
    val id : Int,
    val hour : String? = null,
    val minute : String? = null,
    val dayBegin : String? = null,
    val exerciseName : String? = null,
    val description : String? = null,
    val uniqueIntent : Int? = null,
    val isOn : Boolean? = null

) : Parcelable
