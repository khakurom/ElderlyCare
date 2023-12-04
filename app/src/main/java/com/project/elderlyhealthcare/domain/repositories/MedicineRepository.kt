package com.project.elderlyhealthcare.domain.repositories

import com.project.elderlyhealthcare.data.models.ExerciseEventEntity
import com.project.elderlyhealthcare.data.models.MedicineEventEntity
import com.project.elderlyhealthcare.domain.models.ExerciseEventModel
import com.project.elderlyhealthcare.domain.models.MedicineEventModel
import kotlinx.coroutines.flow.Flow

interface MedicineRepository {

    fun getInsertMedicineEvent (medicineEventEntity: MedicineEventEntity)

    fun getAllMedicineEvent () : Flow<List<MedicineEventModel>>

    fun deleteMedicineEvent (id : Int)

    fun updateMedicineEvent (medicineEventEntity: MedicineEventEntity)

}