package com.project.elderlyhealthcare.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class CustomerInfoModel(
    val customerName: String?,
    val phoneNumber: String?,
    val email: String?,
    val password: String?,
    val elderName: String? = null,
    val elderDob: String? = null,
    val elderGender: String? = null,
    val elderWeight: String? = null,
    val elderHeight: String? = null,
    val address: String? = null,
    val prefecture: String? = null
) : Parcelable
