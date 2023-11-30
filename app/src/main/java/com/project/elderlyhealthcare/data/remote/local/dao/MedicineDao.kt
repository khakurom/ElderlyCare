package com.project.elderlyhealthcare.data.remote.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.elderlyhealthcare.data.models.ExerciseEventEntity
import com.project.elderlyhealthcare.data.models.MedicineEventEntity
import com.project.elderlyhealthcare.utils.Constant.TABLE_EXERCISE_EVENT
import com.project.elderlyhealthcare.utils.Constant.TABLE_MEDICINE_EVENT
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicineDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllMedicineEvent(entity: MedicineEventEntity)

    @Query("SELECT * FROM $TABLE_MEDICINE_EVENT")
    fun getAllMedicineEvent(): Flow<List<MedicineEventEntity>>

    @Query("DELETE FROM $TABLE_MEDICINE_EVENT WHERE id = :id")
    fun deleteMedicineEvent (id : Int)

}