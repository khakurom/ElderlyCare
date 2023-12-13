package com.project.elderlyhealthcare.data.mappers

import com.project.elderlyhealthcare.data.models.ExerciseEventEntity
import com.project.elderlyhealthcare.domain.models.ExerciseEventModel

class ExerciseMapper: Mapper<List<ExerciseEventEntity>, List<ExerciseEventModel>> {
    override fun fromEntity(from: List<ExerciseEventEntity>): List<ExerciseEventModel> {
        return from.map { entity ->
            ExerciseEventModel(
                id = entity.id,
                hour = entity.hour,
                minutes = entity.minutes,
                dayRepeat = entity.dayRepeat,
                dayBegin = entity.dayBegin,
                exerciseName = entity.exerciseName,
                description = entity.description,
                isOn = entity.isOn)
        }
    }

    override fun toEntity(from: List<ExerciseEventModel>): List<ExerciseEventEntity> {
        return from.map { model ->
            ExerciseEventEntity(
                id = model.id,
                hour = model.hour,
                minutes = model.minutes,
                dayRepeat = model.dayRepeat,
                dayBegin = model.dayBegin,
                exerciseName = model.exerciseName,
                description = model.description,
                isOn = model.isOn)
        }
    }
}