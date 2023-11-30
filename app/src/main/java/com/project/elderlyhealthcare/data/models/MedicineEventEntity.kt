package com.project.elderlyhealthcare.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.project.elderlyhealthcare.utils.Constant
import com.squareup.moshi.JsonClass

@Entity(tableName = Constant.TABLE_MEDICINE_EVENT)
@JsonClass(generateAdapter = true)
data class MedicineEventEntity(

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,

    @ColumnInfo(name = "hour")
    val hour : String? = null,

    @ColumnInfo(name = "minutes")
    val minutes : String? = null,

    @ColumnInfo(name = "dayRepeat")
    val dayRepeat : List <String?> ,

    @ColumnInfo(name = "dayBegin")
    val dayBegin : String,

    @ColumnInfo(name = "dayEnd")
    val dayEnd : String ,

    @ColumnInfo(name = "medicineName")
    val medicineName : List <String>,

    @ColumnInfo(name = "medicineDose")
    val medicineDose : List <Int>,

    @ColumnInfo(name = "diseaseName")
    val diseaseName : String
)
