package com.project.elderlyhealthcare.domain.repositories

import com.project.elderlyhealthcare.data.models.HeartRateEntity
import com.project.elderlyhealthcare.data.models.OxygenEntity
import kotlinx.coroutines.flow.Flow

interface HealthParamRepository {

    fun insertHeartRate (heartRateEntity: HeartRateEntity)

    fun getHeartRate (day : String) : Flow< List <Int>>

    fun insertOxygen (oxygenEntity: OxygenEntity)

    fun getOxygen (day : String) : Flow< List <Int>>


}