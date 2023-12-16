package com.project.elderlyhealthcare.presentation.viewmodels.main.usecases

import android.util.Log
import com.project.elderlyhealthcare.data.models.ExerciseEventEntity
import com.project.elderlyhealthcare.data.repositories.ExerciseRepositoryImpl
import com.project.elderlyhealthcare.domain.models.ExerciseEventModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExerciseUseCase @Inject constructor(private val exerciseRepositoryImpl: ExerciseRepositoryImpl) {

    fun insertExerciseEvent(exerciseEvent: ExerciseEventEntity) {
        exerciseRepositoryImpl.getInsertExerciseEvent(exerciseEvent)
    }

    fun getAllExerciseEvent(): Flow<List<ExerciseEventModel>> {
        return exerciseRepositoryImpl.getAllExerciseEvent()
    }

    fun deleteExerciseEvent(id: Int) {
        exerciseRepositoryImpl.deleteExerciseEvent(id)
    }

    fun updateExerciseEvent(exerciseEvent: ExerciseEventEntity) {
        exerciseRepositoryImpl.updateExerciseEvent(exerciseEvent)
    }

    fun updateExerciseEventOnOff(id: Int, isOn: Boolean) {
        exerciseRepositoryImpl.updateExerciseEventOnOff(id, isOn)
    }



    fun getUniqueIntentExercise (id: Int) : Int {
        return exerciseRepositoryImpl.getUniqueIntentExercise(id)
    }


}