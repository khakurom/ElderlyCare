package com.project.elderlyhealthcare.data.mappers

import com.project.elderlyhealthcare.data.models.ExerciseEventEntity
import com.project.elderlyhealthcare.data.models.HeartRateEntity
import com.project.elderlyhealthcare.domain.models.ExerciseEventModel
import com.project.elderlyhealthcare.domain.models.HeartRateModel

class HeartRateMapper: Mapper<List<HeartRateEntity>, List<HeartRateModel>> {
    override fun fromEntity(from: List<HeartRateEntity>): List<HeartRateModel> {
        return from.map { entity ->
            HeartRateModel(
                id = entity.id,
                heartRate = entity.heartRate,
                day = entity.day)

        }
    }

    override fun toEntity(from: List<HeartRateModel>): List<HeartRateEntity> {
        return from.map { model ->
            HeartRateEntity(
                id = model.id,
                heartRate = model.heartRate,
                day = model.day)
        }
    }
}