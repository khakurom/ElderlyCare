package com.project.elderlyhealthcare.data.repositories

import com.project.elderlyhealthcare.data.models.HeartRateEntity
import com.project.elderlyhealthcare.data.models.OxygenEntity
import com.project.elderlyhealthcare.data.remote.local.HealthParamLocalDataSource
import com.project.elderlyhealthcare.domain.repositories.HealthParamRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class HealthParamRepositoryImpl @Inject constructor(
    private val healthParamLocal: HealthParamLocalDataSource
) : HealthParamRepository {
    override fun insertHeartRate(heartRateEntity: HeartRateEntity) {
        healthParamLocal.insertHeartRate(heartRateEntity)
    }

    override fun getHeartRate(day : String): Flow<List<Int>> {
        return healthParamLocal.getHeartRate(day).flowOn(Dispatchers.IO)
    }

    override fun insertOxygen(oxygenEntity: OxygenEntity) {
        healthParamLocal.insertOxygen(oxygenEntity)
    }

    override fun getOxygen(day: String): Flow<List<Int>> {
        return healthParamLocal.getOxygen(day).flowOn(Dispatchers.IO)
    }


}
