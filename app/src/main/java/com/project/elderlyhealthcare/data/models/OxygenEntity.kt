package com.project.elderlyhealthcare.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.project.elderlyhealthcare.utils.Constant
import com.squareup.moshi.JsonClass


@Entity(tableName = Constant.TABLE_OXYGEN)
@JsonClass(generateAdapter = true)
data class OxygenEntity(

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "oxygen")
    val oxygen: Int? = null,

    @ColumnInfo(name = "day")
    val day: String? = null,

)
