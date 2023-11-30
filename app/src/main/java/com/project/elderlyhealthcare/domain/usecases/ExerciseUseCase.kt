package com.project.elderlyhealthcare.domain.usecases

import com.project.elderlyhealthcare.data.models.ExerciseEventEntity
import com.project.elderlyhealthcare.data.repositories.ExerciseRepositoryImpl
import com.project.elderlyhealthcare.domain.models.ExerciseEventModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExerciseUseCase @Inject constructor(private val exerciseRepositoryImpl: ExerciseRepositoryImpl){

    fun insertExerciseEvent (exerciseEvent : ExerciseEventEntity) {
        exerciseRepositoryImpl.getInsertExerciseEvent(exerciseEvent)
    }

    fun getAllExerciseEvent () : Flow<List<ExerciseEventModel>> {
        return exerciseRepositoryImpl.getAllExerciseEvent()
    }

    fun deleteExerciseEvent (id : Int) {
        exerciseRepositoryImpl.deleteExerciseEvent(id)
    }


}