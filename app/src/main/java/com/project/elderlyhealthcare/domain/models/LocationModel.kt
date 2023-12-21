package com.project.elderlyhealthcare.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class LocationModel(
    val lat : Double? = null,
    val lng : Double? = null
) : Parcelable
