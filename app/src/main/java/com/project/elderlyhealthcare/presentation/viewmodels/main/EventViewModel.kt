package com.project.elderlyhealthcare.presentation.viewmodels.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.project.elderlyhealthcare.data.models.ExerciseEventEntity
import com.project.elderlyhealthcare.domain.models.ExerciseEventModel
import com.project.elderlyhealthcare.domain.usecase.ExerciseUseCase
import com.project.elderlyhealthcare.presentation.viewmodels.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(private val exerciseUseCase: ExerciseUseCase) : BaseViewModel() {



    private val _listExerciseEvent = MutableLiveData<List<ExerciseEventModel>?>()
    val listExerciseEvent: MutableLiveData<List<ExerciseEventModel>?> = _listExerciseEvent

    fun insertExerciseEvent (exerciseEvent : ExerciseEventEntity) {
        viewModelScope.launch (Dispatchers.IO){
            exerciseUseCase.insertExerciseEvent(exerciseEvent)
        }
    }

    fun getExerciseEvent () {
        viewModelScope.launch {
            exerciseUseCase.getAllExerciseEvent().collectLatest {
                _listExerciseEvent.postValue(it)
            }
        }
    }

    fun deleteExerciseEvent (id : Int) {
        viewModelScope.launch (Dispatchers.IO){
            exerciseUseCase.deleteExerciseEvent(id)
        }
    }
}