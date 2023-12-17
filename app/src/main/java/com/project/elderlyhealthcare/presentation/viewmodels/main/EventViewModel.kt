package com.project.elderlyhealthcare.presentation.viewmodels.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.project.elderlyhealthcare.data.models.ExerciseEventEntity
import com.project.elderlyhealthcare.data.models.MedicineEventEntity
import com.project.elderlyhealthcare.data.models.ReExaminationEventEntity
import com.project.elderlyhealthcare.domain.models.ExerciseEventModel
import com.project.elderlyhealthcare.domain.models.MedicineEventModel
import com.project.elderlyhealthcare.domain.models.ReExaminationEventModel
import com.project.elderlyhealthcare.presentation.viewmodels.base.BaseViewModel
import com.project.elderlyhealthcare.presentation.viewmodels.main.usecases.ExerciseUseCase
import com.project.elderlyhealthcare.presentation.viewmodels.main.usecases.MedicineUseCase
import com.project.elderlyhealthcare.presentation.viewmodels.main.usecases.ReExaminationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    private val exerciseUseCase: ExerciseUseCase,
    private val medicineUseCase: MedicineUseCase,
    private val reExaminationUseCase: ReExaminationUseCase
) : BaseViewModel() {


    private val _listExerciseEvent = MutableLiveData<List<ExerciseEventModel>?>()
    val listExerciseEvent: MutableLiveData<List<ExerciseEventModel>?> = _listExerciseEvent

    private val _uniqueIntentExercise = MutableLiveData<Int?>()
    val uniqueIntentExercise: MutableLiveData<Int?> = _uniqueIntentExercise

    private val _listMedicineEvent = MutableLiveData<List<MedicineEventModel>?>()
    val listMedicineEvent: MutableLiveData<List<MedicineEventModel>?> = _listMedicineEvent

    private val _listReExEvent = MutableLiveData<List<ReExaminationEventModel>?>()
    val listReExEvent: MutableLiveData<List<ReExaminationEventModel>?> = _listReExEvent


    fun insertExerciseEvent(exerciseEvent: ExerciseEventEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            exerciseUseCase.insertExerciseEvent(exerciseEvent)
        }
    }

    fun getExerciseEvent() {
        viewModelScope.launch() {
            exerciseUseCase.getAllExerciseEvent().collectLatest {
                _listExerciseEvent.value = it
            }
        }
    }

    fun deleteExerciseEvent(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            exerciseUseCase.deleteExerciseEvent(id)
        }
    }

    fun updateExerciseEvent(exerciseEvent: ExerciseEventEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            exerciseUseCase.updateExerciseEvent(exerciseEvent)
        }
    }

    fun updateExerciseEventOnOff(id: Int, isOn: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            exerciseUseCase.updateExerciseEventOnOff(id, isOn)
        }
    }


    fun getUniqueIntentExercise(id: Int) : Int  {
        return exerciseUseCase.getUniqueIntentExercise(id)
    }

    fun insertMedicineEvent(medicineEventEntity: MedicineEventEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            medicineUseCase.insertMedicineEvent(medicineEventEntity)
        }
    }

    fun getMedicineEvent() {
        viewModelScope.launch {
            medicineUseCase.getAllMedicineEvent().collectLatest {
                _listMedicineEvent.postValue(it)
            }
        }
    }

    fun deleteMedicineEvent(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            medicineUseCase.deleteMedicineEvent(id)
        }
    }

    fun updateMedicineEvent(medicineEventEntity: MedicineEventEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            medicineUseCase.updateMedicineEvent(medicineEventEntity)
        }
    }

    fun updateMedicineEventOnOff (id : Int, isOn: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            medicineUseCase.updateMedicineEventOnOff(id, isOn)
        }
    }
    fun getUniqueIntentMedicine(id: Int) : Int  {
        return medicineUseCase.getUniqueIntent(id)
    }

    fun insertReExEvent(reExaminationEventEntity: ReExaminationEventEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            reExaminationUseCase.insertReExEvent(reExaminationEventEntity)
        }
    }

    fun getReExEvent() {
        viewModelScope.launch {
            reExaminationUseCase.getReExEvent().collectLatest {
                _listReExEvent.postValue(it)
            }
        }
    }

    fun deleteReExEvent(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            reExaminationUseCase.deleteReExEvent(id)
        }
    }

    fun updateReExEvent(reExaminationEventEntity: ReExaminationEventEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            reExaminationUseCase.updateReExEvent(reExaminationEventEntity)
        }
    }

    fun updateReExEventOnOff (id: Int, isOn: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            reExaminationUseCase.updateReExEventOnOff(id, isOn)
        }
    }

    fun getUniqueIntentReEx (id: Int) : Int{
        return reExaminationUseCase.getUniqueIntentRex(id)
    }
}