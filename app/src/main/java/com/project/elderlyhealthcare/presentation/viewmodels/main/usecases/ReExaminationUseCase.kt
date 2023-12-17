package com.project.elderlyhealthcare.presentation.viewmodels.main.usecases

import com.project.elderlyhealthcare.data.models.ReExaminationEventEntity
import com.project.elderlyhealthcare.data.repositories.ReExaminationRepositoryImpl
import com.project.elderlyhealthcare.domain.models.ReExaminationEventModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReExaminationUseCase @Inject constructor(private val reExaminationRepositoryImpl: ReExaminationRepositoryImpl) {

    fun insertReExEvent(reExaminationEventEntity: ReExaminationEventEntity) {
        reExaminationRepositoryImpl.insertReExEvent(reExaminationEventEntity)
    }

    fun getReExEvent () : Flow<List <ReExaminationEventModel>> {
        return reExaminationRepositoryImpl.getReExEvent()
    }

    fun deleteReExEvent (id : Int) {
        reExaminationRepositoryImpl.deleteReExEvent(id)
    }

    fun updateReExEvent (reExaminationEventEntity: ReExaminationEventEntity) {
        reExaminationRepositoryImpl.updateReExEvent(reExaminationEventEntity)
    }

     fun updateReExEventOnOff(id: Int, isOn: Boolean) {
         reExaminationRepositoryImpl.updateReExEventOnOff(id,isOn)
    }

     fun getUniqueIntentRex(id: Int): Int {
        return reExaminationRepositoryImpl.getUniqueIntentRex(id)
    }


}