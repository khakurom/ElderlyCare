package com.project.elderlyhealthcare.data.remote.local

import com.project.elderlyhealthcare.data.models.ExerciseEventEntity
import com.project.elderlyhealthcare.data.models.MedicineEventEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class MedicineLocalDataSource @Inject constructor(private val appDatabase: AppDatabase) {
    fun insertMedicineEvent (medicineEventEntity : MedicineEventEntity ) {
        appDatabase.medicineDao().insertAllMedicineEvent(medicineEventEntity)
    }

    fun getAllMedicineEvent () : Flow <List <MedicineEventEntity>> {
        return appDatabase.medicineDao().getAllMedicineEvent()
    }

    fun deleteMedicineEvent (id : Int) {
        appDatabase.medicineDao().deleteMedicineEvent(id)
    }
}