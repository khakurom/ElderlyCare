package com.project.elderlyhealthcare.data.repositories

import com.project.elderlyhealthcare.data.mappers.ReExaminationMapper
import com.project.elderlyhealthcare.data.models.ReExaminationEventEntity
import com.project.elderlyhealthcare.data.remote.local.ReExaminationLocalDataSource
import com.project.elderlyhealthcare.domain.models.ReExaminationEventModel
import com.project.elderlyhealthcare.domain.repositories.ReExaminationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ReExaminationRepositoryImpl @Inject constructor(
    private val reExaminationLocalDataSource: ReExaminationLocalDataSource
) : ReExaminationRepository {
    override fun insertReExEvent(reExaminationEventEntity: ReExaminationEventEntity) {
        reExaminationLocalDataSource.insertReExEvent(reExaminationEventEntity)
    }

    override fun getReExEvent(): Flow<List<ReExaminationEventModel>> {
        return reExaminationLocalDataSource.getAllReExEvent().map {
            ReExaminationMapper().fromEntity(it)
        }.flowOn(Dispatchers.IO)
    }

    override fun deleteReExEvent(id: Int) {
        reExaminationLocalDataSource.deleteReExEvent(id)
    }

    override fun updateReExEvent(reExaminationEventEntity: ReExaminationEventEntity) {
        reExaminationLocalDataSource.updateReExEvent(reExaminationEventEntity)
    }

    override fun updateReExEventOnOff(id: Int, isOn: Boolean) {
        reExaminationLocalDataSource.updateReExEventOnOff(id,isOn)
    }

    override fun getUniqueIntentRex(id: Int): Int {
        return reExaminationLocalDataSource.getUniqueIntentRex(id)
    }


}
