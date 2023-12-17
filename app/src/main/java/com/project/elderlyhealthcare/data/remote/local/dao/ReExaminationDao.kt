package com.project.elderlyhealthcare.data.remote.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.project.elderlyhealthcare.data.models.MedicineEventEntity
import com.project.elderlyhealthcare.data.models.ReExaminationEventEntity
import com.project.elderlyhealthcare.utils.Constant.TABLE_MEDICINE_EVENT
import com.project.elderlyhealthcare.utils.Constant.TABLE_RE_EXAM_EVENT
import kotlinx.coroutines.flow.Flow

@Dao
interface ReExaminationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllReExEvent(entity: ReExaminationEventEntity)

    @Query("SELECT * FROM $TABLE_RE_EXAM_EVENT")
    fun getAllReExEvent(): Flow<List<ReExaminationEventEntity>>

    @Query("DELETE FROM $TABLE_RE_EXAM_EVENT WHERE id = :id")
    fun deleteReExEvent (id : Int)

    @Update
    fun updateReExEvent (reExaminationEventEntity: ReExaminationEventEntity)

    @Query("UPDATE $TABLE_RE_EXAM_EVENT SET isOn = :isOn WHERE id = :id")
    fun updateReExEventOnOff (id : Int, isOn : Boolean)

    @Query("SELECT uniqueIntent FROM $TABLE_RE_EXAM_EVENT WHERE id = :id")
    fun getUniqueIntentReEx (id : Int) : Int

}