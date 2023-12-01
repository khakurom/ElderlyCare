package com.project.elderlyhealthcare.data.mappers

import com.project.elderlyhealthcare.data.models.MedicineEventEntity
import com.project.elderlyhealthcare.domain.models.MedicineEventModel

class MedicineMapper : Mapper<List<MedicineEventEntity>, List<MedicineEventModel>>,
    MapperObject<MedicineEventEntity, MedicineEventModel> {
    override fun fromEntity(from: List<MedicineEventEntity>): List<MedicineEventModel> {
        return from.map { entity ->
            MedicineEventModel(
                id = entity.id,
                hour = entity.hour,
                minutes = entity.minutes,
                dayRepeat = entity.dayRepeat,
                dayBegin = entity.dayBegin,
                dayEnd = entity.dayEnd,
                medicineName = entity.medicineName,
                medicineDose = entity.medicineDose,
                diseaseName = entity.diseaseName
            )
        }
    }

    override fun toEntity(from: List<MedicineEventModel>): List<MedicineEventEntity> {
        return from.map { model ->
            MedicineEventEntity(
                id = model.id,
                hour = model.hour,
                minutes = model.minutes,
                dayRepeat = model.dayRepeat,
                dayBegin = model.dayBegin,
                dayEnd = model.dayEnd,
                medicineName = model.medicineName,
                medicineDose = model.medicineDose,
                diseaseName = model.diseaseName
            )
        }
    }

    override fun fromObjectEntity(from: MedicineEventEntity): MedicineEventModel {
        return MedicineEventModel(
            id = from.id,
            hour = from.hour,
            minutes = from.minutes,
            dayRepeat = from.dayRepeat,
            dayBegin = from.dayBegin,
            dayEnd = from.dayEnd,
            medicineName = from.medicineName,
            medicineDose = from.medicineDose,
            diseaseName = from.diseaseName
        )
    }

    override fun toObjectEntity(from: MedicineEventModel): MedicineEventEntity {
        return MedicineEventEntity(
            id = from.id,
            hour = from.hour,
            minutes = from.minutes,
            dayRepeat = from.dayRepeat,
            dayBegin = from.dayBegin,
            dayEnd = from.dayEnd,
            medicineName = from.medicineName,
            medicineDose = from.medicineDose,
            diseaseName = from.diseaseName
        )
    }
}