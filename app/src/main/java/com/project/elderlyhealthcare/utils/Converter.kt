package com.project.elderlyhealthcare.utils

import androidx.room.TypeConverter

class Converter {
    @TypeConverter
    fun fromString(value: String?): List<String>? {
        return value?.split(",")?.map { it.trim() }
    }

    @TypeConverter
    fun fromList(list: List<String>?): String {
        return list?.joinToString(",") ?: ""
    }

    @TypeConverter
    fun fromIntList(list: List<Int>?): String {
        return list?.joinToString(",") { it.toString() } ?: ""
    }

    @TypeConverter
    fun toIntList(value: String): List<Int> {
        return if (value.isNotBlank()) {
            value.split(",").map { it.toInt() }
        } else {
            emptyList()
        }
    }

}