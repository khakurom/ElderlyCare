package com.project.elderlyhealthcare.data.repositories

import com.project.elderlyhealthcare.data.mappers.MedicineMapper
import com.project.elderlyhealthcare.data.models.ExerciseEventEntity
import com.project.elderlyhealthcare.data.models.MedicineEventEntity
import com.project.elderlyhealthcare.data.remote.local.MedicineLocalDataSource
import com.project.elderlyhealthcare.domain.models.MedicineEventModel
import com.project.elderlyhealthcare.domain.repositories.MedicineRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MedicineRepositoryImpl @Inject constructor(
    private val medicineLocalDataSource: MedicineLocalDataSource
) : MedicineRepository {
    override fun getInsertMedicineEvent(medicineEventEntity: MedicineEventEntity) {
        medicineLocalDataSource.insertMedicineEvent(medicineEventEntity)
    }

    override fun getAllMedicineEvent(): Flow<List<MedicineEventModel>> {
        return medicineLocalDataSource.getAllMedicineEvent().map {
            MedicineMapper().fromEntity(it)
        }.flowOn(Dispatchers.IO)
    }

    override fun deleteMedicineEvent(id: Int) {
        medicineLocalDataSource.deleteMedicineEvent(id)
    }

    override fun updateMedicineEvent(medicineEventEntity: MedicineEventEntity) {
        medicineLocalDataSource.updateMedicineEvent(medicineEventEntity)
    }
}
