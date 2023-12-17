package com.project.elderlyhealthcare.data.mappers

import com.project.elderlyhealthcare.data.models.ReExaminationEventEntity
import com.project.elderlyhealthcare.domain.models.ReExaminationEventModel

class ReExaminationMapper : Mapper<List<ReExaminationEventEntity>, List<ReExaminationEventModel>> {
    override fun fromEntity(from: List<ReExaminationEventEntity>): List<ReExaminationEventModel> {
        return from.map { entity ->
            ReExaminationEventModel(
                id = entity.id,
                hour = entity.hour,
                minutes = entity.minutes,
                dayBegin = entity.dayBegin,
                diseaseName = entity.diseaseName,
                address = entity.address,
                uniqueIntent = entity.uniqueIntent,
                isOn = entity.isOn
            )
        }
    }

    override fun toEntity(from: List<ReExaminationEventModel>): List<ReExaminationEventEntity> {
        return from.map { model ->
            ReExaminationEventEntity(
                id = model.id,
                hour = model.hour,
                minutes = model.minutes,
                dayBegin = model.dayBegin,
                diseaseName = model.diseaseName,
                address = model.address,
                uniqueIntent = model.uniqueIntent,
                isOn = model.isOn
            )
        }
    }


}