package com.project.elderlyhealthcare.domain.usecases

import com.project.elderlyhealthcare.data.models.HeartRateEntity
import com.project.elderlyhealthcare.data.models.OxygenEntity
import com.project.elderlyhealthcare.data.repositories.HealthParamRepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HealthParamUseCase @Inject constructor(private val healthParamRepositoryImpl: HealthParamRepositoryImpl) {


    fun insertHeartRate(heartRateEntity: HeartRateEntity) {
        healthParamRepositoryImpl.insertHeartRate(heartRateEntity)
    }

    fun getHeartRate(day : String): Flow<List<Int>> {
        return healthParamRepositoryImpl.getHeartRate(day)
    }

    fun insertOxygen (oxygenEntity: OxygenEntity) {
        healthParamRepositoryImpl.insertOxygen(oxygenEntity)
    }

    fun getOxygen (day : String): Flow<List<Int>> {
        return healthParamRepositoryImpl.getOxygen(day)
    }

}