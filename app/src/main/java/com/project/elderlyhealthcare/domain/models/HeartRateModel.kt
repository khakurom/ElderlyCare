package com.project.elderlyhealthcare.domain.models

data class HeartRateModel (
    val id : Int,
    val heartRate : Int? = null,
    val day : String? = null
)