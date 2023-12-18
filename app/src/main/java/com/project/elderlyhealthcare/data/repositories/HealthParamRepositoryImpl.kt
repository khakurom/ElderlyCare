package com.project.elderlyhealthcare.data.repositories

import com.project.elderlyhealthcare.data.mappers.ExerciseMapper
import com.project.elderlyhealthcare.data.mappers.HeartRateMapper
import com.project.elderlyhealthcare.data.models.ExerciseEventEntity
import com.project.elderlyhealthcare.data.models.HeartRateEntity
import com.project.elderlyhealthcare.data.remote.local.ExerciseLocalDataSource
import com.project.elderlyhealthcare.data.remote.local.HealthParamLocalDataSource
import com.project.elderlyhealthcare.domain.models.ExerciseEventModel
import com.project.elderlyhealthcare.domain.models.HeartRateModel
import com.project.elderlyhealthcare.domain.repositories.ExerciseRepository
import com.project.elderlyhealthcare.domain.repositories.HealthParamRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
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


}
