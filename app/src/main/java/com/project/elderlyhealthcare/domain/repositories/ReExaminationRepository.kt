package com.project.elderlyhealthcare.domain.repositories

import com.project.elderlyhealthcare.data.models.MedicineEventEntity
import com.project.elderlyhealthcare.data.models.ReExaminationEventEntity
import com.project.elderlyhealthcare.domain.models.ReExaminationEventModel
import kotlinx.coroutines.flow.Flow

interface ReExaminationRepository {

    fun insertReExEvent (reExaminationEventEntity: ReExaminationEventEntity)

    fun getReExEvent () : Flow< List <ReExaminationEventModel>>

    fun deleteReExEvent (id : Int)

    fun updateReExEvent (reExaminationEventEntity: ReExaminationEventEntity)

    fun updateReExEventOnOff (id : Int, isOn : Boolean)

    fun getUniqueIntentRex (id : Int) : Int
}