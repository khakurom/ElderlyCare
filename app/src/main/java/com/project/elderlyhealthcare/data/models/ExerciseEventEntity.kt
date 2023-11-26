package com.project.elderlyhealthcare.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.project.elderlyhealthcare.utils.Constant
import com.squareup.moshi.JsonClass


@Entity(tableName = Constant.TABLE_EXERCISE_EVENT)
@JsonClass(generateAdapter = true)
data class ExerciseEventEntity(

    @ColumnInfo (name = "id")
    @PrimaryKey (autoGenerate = true)
    val id : Int = 0,

    @ColumnInfo (name = "hour")
    val hour : String? = null,

    @ColumnInfo (name = "minutes")
    val minutes : String? = null,

    @ColumnInfo (name = "dayRepeat")
    val dayRepeat : List <String?>,

    @ColumnInfo (name = "dayBegin")
    val dayBegin : String? = null,

    @ColumnInfo (name = "exerciseName")
    val exerciseName : String? = null,

    @ColumnInfo (name = "description")
    val description : String? = null

)
