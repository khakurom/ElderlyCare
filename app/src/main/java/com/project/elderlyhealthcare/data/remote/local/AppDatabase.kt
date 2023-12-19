package com.project.elderlyhealthcare.data.remote.local


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.project.elderlyhealthcare.data.models.ExerciseEventEntity
import com.project.elderlyhealthcare.data.models.HeartRateEntity
import com.project.elderlyhealthcare.data.models.MedicineEventEntity
import com.project.elderlyhealthcare.data.models.OxygenEntity
import com.project.elderlyhealthcare.data.models.ReExaminationEventEntity
import com.project.elderlyhealthcare.data.remote.local.dao.ExerciseDao
import com.project.elderlyhealthcare.data.remote.local.dao.HealthParamAvgDao
import com.project.elderlyhealthcare.data.remote.local.dao.MedicineDao
import com.project.elderlyhealthcare.data.remote.local.dao.ReExaminationDao
import com.project.elderlyhealthcare.utils.Converter

@Database(
    entities = [ExerciseEventEntity::class, MedicineEventEntity::class, ReExaminationEventEntity ::class,HeartRateEntity ::class, OxygenEntity ::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun exerciseDao(): ExerciseDao

    abstract fun medicineDao(): MedicineDao

    abstract fun reExaminationDao() : ReExaminationDao

    abstract fun healthParamAvgDao () : HealthParamAvgDao

}