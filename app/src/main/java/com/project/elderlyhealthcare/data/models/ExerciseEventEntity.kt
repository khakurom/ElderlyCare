package com.project.elderlyhealthcare.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.project.elderlyhealthcare.utils.Constant
import com.squareup.moshi.JsonClass


@Entity(tableName = Constant.TABLE_EXERCISE_EVENT)
@JsonClass(generateAdapter = true)
data class ExerciseEventEntity(

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "hour")
    val hour: String? = null,

    @ColumnInfo(name = "minute")
    val minute: String? = null,

    @ColumnInfo(name = "dayBegin")
    val dayBegin: String? = null,

    @ColumnInfo(name = "exerciseName")
    val exerciseName: String? = null,

    @ColumnInfo(name = "description")
    val description: String? = null,

    @ColumnInfo(name = "uniqueIntent")
    val uniqueIntent: Int? = 0,

    @ColumnInfo(name = "isOn")
    val isOn: Boolean? = null


)
