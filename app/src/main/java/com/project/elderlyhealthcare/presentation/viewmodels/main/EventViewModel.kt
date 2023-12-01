package com.project.elderlyhealthcare.presentation.viewmodels.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.project.elderlyhealthcare.data.models.ExerciseEventEntity
import com.project.elderlyhealthcare.data.models.MedicineEventEntity
import com.project.elderlyhealthcare.domain.models.ExerciseEventModel
import com.project.elderlyhealthcare.domain.models.MedicineEventModel
import com.project.elderlyhealthcare.domain.usecases.ExerciseUseCase
import com.project.elderlyhealthcare.domain.usecases.MedicineUseCase
import com.project.elderlyhealthcare.presentation.viewmodels.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    private val exerciseUseCase: ExerciseUseCase,
    private val medicineUseCase: MedicineUseCase
) : BaseViewModel() {


    private val _listExerciseEvent = MutableLiveData<List<ExerciseEventModel>?>()
    val listExerciseEvent: MutableLiveData<List<ExerciseEventModel>?> = _listExerciseEvent

    private val _listMedicineEvent = MutableLiveData<List<MedicineEventModel>?>()
    val listMedicineEvent: MutableLiveData<List<MedicineEventModel>?> = _listMedicineEvent

    fun insertExerciseEvent(exerciseEvent: ExerciseEventEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            exerciseUseCase.insertExerciseEvent(exerciseEvent)
        }
    }

    fun getExerciseEvent() {
        viewModelScope.launch {
            exerciseUseCase.getAllExerciseEvent().collectLatest {
                _listExerciseEvent.postValue(it)
            }
        }
    }

    fun deleteExerciseEvent(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            exerciseUseCase.deleteExerciseEvent(id)
        }
    }

    fun insertMedicineEvent(medicineEventEntity: MedicineEventEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            medicineUseCase.insertMedicineEvent(medicineEventEntity)
        }
    }

    fun getMedicineEvent() {
        viewModelScope.launch  {
            medicineUseCase.getAllMedicineEvent().collectLatest {
                _listMedicineEvent.postValue(it)
            }
        }
    }

    fun deleteMedicineEvent (id : Int) {
        viewModelScope.launch (Dispatchers.IO) {
            medicineUseCase.deleteMedicineEvent(id)
        }
    }

    fun updateMedicineEvent (medicineEventEntity: MedicineEventEntity) {
        viewModelScope.launch (Dispatchers.IO) {
            medicineUseCase.updateMedicineEvent(medicineEventEntity)
        }
    }
}