package com.project.elderlyhealthcare.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class CustomerInfoModel(
    val customerName: String?,
    val phoneNumber: String?,
    val accountName: String?,
    val password: String?,
    var elderName: String? = null,
    var elderDob: String? = null,
    var elderGender: String? = null,
    var elderWeight: Int? = null,
    var elderHeight: Int? = null,
    var address: String? = null,
    var prefecture: String? = null
) : Parcelable
