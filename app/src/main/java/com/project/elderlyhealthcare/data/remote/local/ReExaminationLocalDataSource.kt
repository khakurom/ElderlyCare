package com.project.elderlyhealthcare.data.remote.local

import com.project.elderlyhealthcare.data.models.ReExaminationEventEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class ReExaminationLocalDataSource @Inject constructor(private val appDatabase: AppDatabase) {
    fun insertReExEvent (reExEntity : ReExaminationEventEntity ){
        appDatabase.reExaminationDao().insertAllReExEvent(reExEntity)
    }

    fun getAllReExEvent () : Flow<List<ReExaminationEventEntity>> {
        return appDatabase.reExaminationDao().getAllReExEvent()
    }

    fun deleteReExEvent (id : Int) {
        appDatabase.reExaminationDao().deleteReExEvent(id)
    }

    fun updateReExEvent (reExaminationEventEntity: ReExaminationEventEntity) {
        appDatabase.reExaminationDao().updateReExEvent(reExaminationEventEntity)
    }

}