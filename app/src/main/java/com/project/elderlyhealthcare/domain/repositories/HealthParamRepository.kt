package com.project.elderlyhealthcare.domain.repositories

import com.project.elderlyhealthcare.data.models.HeartRateEntity
import com.project.elderlyhealthcare.data.models.MedicineEventEntity
import com.project.elderlyhealthcare.data.models.ReExaminationEventEntity
import com.project.elderlyhealthcare.domain.models.HeartRateModel
import com.project.elderlyhealthcare.domain.models.ReExaminationEventModel
import kotlinx.coroutines.flow.Flow

interface HealthParamRepository {

    fun insertHeartRate (heartRateEntity: HeartRateEntity)

    fun getHeartRate (day : String) : Flow< List <Int>>


}