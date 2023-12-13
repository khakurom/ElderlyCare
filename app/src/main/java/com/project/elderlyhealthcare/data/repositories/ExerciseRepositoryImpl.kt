package com.project.elderlyhealthcare.data.repositories

import android.util.Log
import com.project.elderlyhealthcare.data.mappers.ExerciseMapper
import com.project.elderlyhealthcare.data.models.ExerciseEventEntity
import com.project.elderlyhealthcare.data.remote.local.ExerciseLocalDataSource
import com.project.elderlyhealthcare.domain.models.ExerciseEventModel
import com.project.elderlyhealthcare.domain.repositories.ExerciseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ExerciseRepositoryImpl @Inject constructor(
    private val localExercise: ExerciseLocalDataSource
) : ExerciseRepository {
    override fun getInsertExerciseEvent(exerciseEventEntity: ExerciseEventEntity) {
        localExercise.insertExerciseEvent(exerciseEventEntity)
    }

    override fun getAllExerciseEvent(): Flow<List<ExerciseEventModel>> {
        return localExercise.getAllExerciseEvent().map {
            ExerciseMapper().fromEntity(it)
        }
    }

    override fun deleteExerciseEvent(id: Int) {
        localExercise.deleteExerciseEvent(id)
    }

    override fun updateExerciseEvent(exerciseEventEntity: ExerciseEventEntity) {
        localExercise.updateExerciseEvent(exerciseEventEntity)
    }

    override fun updateExerciseEventOnOff(id: Int, isOn: Boolean) {
        localExercise.updateExerciseEventOnOff(id, isOn)
    }

}
