package com.project.elderlyhealthcare.data.remote.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.project.elderlyhealthcare.data.models.MedicineEventEntity
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

    @Update
    fun updateMedicineEvent (entity: MedicineEventEntity)

    @Query("UPDATE $TABLE_MEDICINE_EVENT SET isOn = :isOn WHERE id = :id")
    fun updateMedicineEventOnOff (id : Int, isOn : Boolean)

    @Query("SELECT uniqueIntent FROM $TABLE_MEDICINE_EVENT WHERE id = :id")
    fun getUniqueIntentMedicine (id : Int) : Int

}