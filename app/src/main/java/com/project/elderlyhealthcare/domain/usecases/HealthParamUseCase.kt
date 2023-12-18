package com.project.elderlyhealthcare.domain.usecases

import com.project.elderlyhealthcare.data.models.HeartRateEntity
import com.project.elderlyhealthcare.data.repositories.HealthParamRepositoryImpl
import com.project.elderlyhealthcare.domain.models.HeartRateModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HealthParamUseCase @Inject constructor(private val healthParamRepositoryImpl: HealthParamRepositoryImpl) {


    fun insertHeartRate(heartRateEntity: HeartRateEntity) {
        healthParamRepositoryImpl.insertHeartRate(heartRateEntity)
    }

    fun getHeartRate(day : String): Flow<List<Int>> {
        return healthParamRepositoryImpl.getHeartRate(day)
    }

}