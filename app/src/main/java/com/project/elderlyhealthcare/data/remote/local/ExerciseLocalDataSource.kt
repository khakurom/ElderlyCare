package com.project.elderlyhealthcare.data.remote.local

import com.project.elderlyhealthcare.data.models.ExerciseEventEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class ExerciseLocalDataSource @Inject constructor(private val appDatabase: AppDatabase) {
    fun insertExerciseEvent (exerciseEntity : ExerciseEventEntity ){
        appDatabase.exerciseDao().insertAllExerciseEvent(exerciseEntity)
    }

    fun getAllExerciseEvent(): Flow<List<ExerciseEventEntity>> {
        return appDatabase.exerciseDao().getAllExerciseEvent()
    }

    fun deleteExerciseEvent (id : Int) {
        return appDatabase.exerciseDao().deleteExerciseEvent(id)
    }
}