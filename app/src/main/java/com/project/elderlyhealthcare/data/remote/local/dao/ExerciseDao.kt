package com.project.elderlyhealthcare.data.remote.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.project.elderlyhealthcare.data.models.ExerciseEventEntity
import com.project.elderlyhealthcare.utils.Constant.TABLE_EXERCISE_EVENT
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllExerciseEvent(entity: ExerciseEventEntity)

    @Query("SELECT * FROM $TABLE_EXERCISE_EVENT")
    fun getAllExerciseEvent(): Flow<List<ExerciseEventEntity>>

    @Query("DELETE FROM $TABLE_EXERCISE_EVENT WHERE id = :id")
    fun deleteExerciseEvent(id: Int)

    @Update
    fun updateExerciseEvent (exerciseEventEntity: ExerciseEventEntity)

    @Query("UPDATE $TABLE_EXERCISE_EVENT SET uniqueIntent = :uniqueIntent WHERE id = :id")
    fun updateUniqueIntentExercise (uniqueIntent : Int?, id : Int)

    @Query("UPDATE $TABLE_EXERCISE_EVENT SET isOn = :isOn WHERE id = :id")
    fun updateExerciseEventOnOff (id : Int, isOn : Boolean)

    @Query("SELECT uniqueIntent FROM $TABLE_EXERCISE_EVENT WHERE id = :id")
    fun getUniqueIntentExercise (id : Int) : Flow <Int>

}