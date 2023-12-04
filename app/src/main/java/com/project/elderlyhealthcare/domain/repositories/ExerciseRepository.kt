package com.project.elderlyhealthcare.domain.repositories

import com.project.elderlyhealthcare.data.models.ExerciseEventEntity
import com.project.elderlyhealthcare.domain.models.ExerciseEventModel
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {

    fun getInsertExerciseEvent (exerciseEventEntity: ExerciseEventEntity)

    fun getAllExerciseEvent () : Flow <List<ExerciseEventModel>>

    fun deleteExerciseEvent (id : Int)

    fun updateExerciseEvent (exerciseEventEntity: ExerciseEventEntity)

}