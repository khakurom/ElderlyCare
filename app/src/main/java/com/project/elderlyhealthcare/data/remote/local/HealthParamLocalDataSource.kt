package com.project.elderlyhealthcare.data.remote.local

import com.project.elderlyhealthcare.data.models.HeartRateEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class HealthParamLocalDataSource @Inject constructor(private val appDatabase: AppDatabase) {
    fun insertHeartRate  (heartRateEntity: HeartRateEntity ){
        appDatabase.healthParamAvgDao().insertHeartRate(heartRateEntity)
    }

    fun getHeartRate (day : String) : Flow<List<Int>> {
        return appDatabase.healthParamAvgDao().getAllHeartRate(day)
    }
}