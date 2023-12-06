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
    val id : Int = 0,

    @ColumnInfo(name = "hour")
    val hour : String? = null,

    @ColumnInfo(name = "minutes")
    val minutes : String? = null,

    @ColumnInfo(name = "dayBegin")
    val dayBegin : String? = null,

    @ColumnInfo(name = "nameDisease")
    val diseaseName : String? = null,

    @ColumnInfo(name = "address")
    val address : String? = null

)
