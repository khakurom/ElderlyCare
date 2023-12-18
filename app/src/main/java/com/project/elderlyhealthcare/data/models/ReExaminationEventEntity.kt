package com.project.elderlyhealthcare.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.project.elderlyhealthcare.utils.Constant
import com.squareup.moshi.JsonClass


@Entity(tableName = Constant.TABLE_RE_EXAM_EVENT)
@JsonClass(generateAdapter = true)
data class ReExaminationEventEntity(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "hour")
    val hour: String,

    @ColumnInfo(name = "minute")
    val minute: String,

    @ColumnInfo(name = "dayBegin")
    val dayBegin: String,

    @ColumnInfo(name = "nameDisease")
    val diseaseName: String,

    @ColumnInfo(name = "address")
    val address: String? = null,

    @ColumnInfo(name = "uniqueIntent")
    val uniqueIntent: Int,

    @ColumnInfo(name = "isOn")
    val isOn: Boolean,

    )
