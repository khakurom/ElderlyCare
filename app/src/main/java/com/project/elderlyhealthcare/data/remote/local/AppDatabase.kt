package com.project.elderlyhealthcare.data.remote.local


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.project.elderlyhealthcare.data.models.ExerciseEventEntity
import com.project.elderlyhealthcare.data.models.MedicineEventEntity
import com.project.elderlyhealthcare.data.remote.local.dao.ExerciseDao
import com.project.elderlyhealthcare.data.remote.local.dao.MedicineDao
import com.project.elderlyhealthcare.utils.Converter

@Database(
    entities = [ExerciseEventEntity::class, MedicineEventEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun exerciseDao(): ExerciseDao

    abstract fun medicineDao(): MedicineDao

}