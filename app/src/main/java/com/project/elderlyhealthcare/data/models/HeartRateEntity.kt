package com.project.elderlyhealthcare.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.project.elderlyhealthcare.utils.Constant
import com.squareup.moshi.JsonClass


@Entity(tableName = Constant.TABLE_HEART_RATE)
@JsonClass(generateAdapter = true)
data class HeartRateEntity(

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "heartRate")
    val heartRate: Int? = null,

    @ColumnInfo(name = "day")
    val day: String? = null,

)
