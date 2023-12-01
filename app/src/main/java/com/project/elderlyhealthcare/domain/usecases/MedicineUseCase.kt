package com.project.elderlyhealthcare.domain.usecases

import com.project.elderlyhealthcare.data.models.MedicineEventEntity
import com.project.elderlyhealthcare.data.repositories.MedicineRepositoryImpl
import com.project.elderlyhealthcare.domain.models.MedicineEventModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MedicineUseCase @Inject constructor(private val medicineRepositoryImpl: MedicineRepositoryImpl) {

    fun insertMedicineEvent(medicineEventEntity: MedicineEventEntity) {
        medicineRepositoryImpl.getInsertMedicineEvent(medicineEventEntity)
    }

    fun getAllMedicineEvent () : Flow<List<MedicineEventModel>> {
        return medicineRepositoryImpl.getAllMedicineEvent()
    }

    fun deleteMedicineEvent (id : Int) {
        medicineRepositoryImpl.deleteMedicineEvent(id)
    }

    fun updateMedicineEvent (medicineEventEntity: MedicineEventEntity) {
        medicineRepositoryImpl.updateMedicineEvent(medicineEventEntity)
    }
}