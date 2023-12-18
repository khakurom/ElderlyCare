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
                minute = entity.minute,
                dayBegin = entity.dayBegin,
                dayEnd = entity.dayEnd,
                medicineName = entity.medicineName,
                medicineDose = entity.medicineDose,
                diseaseName = entity.diseaseName,
                uniqueIntent = entity.uniqueIntent,
                isOn = entity.isOn
            )
        }
    }

    override fun toEntity(from: List<MedicineEventModel>): List<MedicineEventEntity> {
        return from.map { model ->
            MedicineEventEntity(
                id = model.id,
                hour = model.hour,
                minute = model.minute,
                dayBegin = model.dayBegin,
                dayEnd = model.dayEnd,
                medicineName = model.medicineName,
                medicineDose = model.medicineDose,
                diseaseName = model.diseaseName,
                uniqueIntent = model.uniqueIntent,
                isOn = model.isOn
            )
        }
    }

    override fun fromObjectEntity(from: MedicineEventEntity): MedicineEventModel {
        return MedicineEventModel(
            id = from.id,
            hour = from.hour,
            minute = from.minute,
            dayBegin = from.dayBegin,
            dayEnd = from.dayEnd,
            medicineName = from.medicineName,
            medicineDose = from.medicineDose,
            diseaseName = from.diseaseName,
            uniqueIntent = from.uniqueIntent,
            isOn = from.isOn
        )
    }

    override fun toObjectEntity(from: MedicineEventModel): MedicineEventEntity {
        return MedicineEventEntity(
            id = from.id,
            hour = from.hour,
            minute = from.minute,
            dayBegin = from.dayBegin,
            dayEnd = from.dayEnd,
            medicineName = from.medicineName,
            medicineDose = from.medicineDose,
            diseaseName = from.diseaseName,
            uniqueIntent = from.uniqueIntent,
            isOn = from.isOn
        )
    }
}