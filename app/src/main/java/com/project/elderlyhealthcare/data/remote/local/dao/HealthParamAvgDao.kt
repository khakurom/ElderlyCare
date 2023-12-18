package com.project.elderlyhealthcare.data.remote.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.project.elderlyhealthcare.data.models.ExerciseEventEntity
import com.project.elderlyhealthcare.data.models.HeartRateEntity
import com.project.elderlyhealthcare.utils.Constant.TABLE_EXERCISE_EVENT
import com.project.elderlyhealthcare.utils.Constant.TABLE_HEART_RATE
import kotlinx.coroutines.flow.Flow

@Dao
interface HealthParamAvgDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHeartRate (entity: HeartRateEntity)

    @Query("SELECT heartRate FROM $TABLE_HEART_RATE WHERE day = :day")
    fun getAllHeartRate (day : String): Flow<List<Int>>

}