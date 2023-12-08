package com.project.elderlyhealthcare.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class CustomerInfoModel(
    val customerName: String? = null,
    val phoneNumber: String? = null,
    val password: String? = null,
    var elderName: String? = null,
    var elderDob: String? = null,
    var elderGender: String? = null,
    var elderWeight: Int? = null,
    var elderHeight: Int? = null,
    var address: String? = null,
    var prefecture: String? = null
) : Parcelable
