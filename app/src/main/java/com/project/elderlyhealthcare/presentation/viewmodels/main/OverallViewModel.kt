package com.project.elderlyhealthcare.presentation.viewmodels.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.project.elderlyhealthcare.data.models.HeartRateEntity
import com.project.elderlyhealthcare.data.models.OxygenEntity
import com.project.elderlyhealthcare.domain.usecases.HealthParamUseCase
import com.project.elderlyhealthcare.presentation.viewmodels.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class OverallViewModel @Inject constructor(private val healthParamUseCase: HealthParamUseCase) : BaseViewModel () {


    private val _listHeartRate = MutableLiveData<List<Int>?>()
    val listHeartRate: MutableLiveData<List<Int>?> = _listHeartRate

    private val _listOxygen = MutableLiveData<List<Int>?>()
    val listOxygen: MutableLiveData<List<Int>?> = _listOxygen


    fun insertHeartRate (heartRateEntity: HeartRateEntity) {
        viewModelScope.launch (Dispatchers.IO) {
            healthParamUseCase.insertHeartRate(heartRateEntity)
        }
    }

    fun getHeartRate (day : String) {
        viewModelScope.launch {
            healthParamUseCase.getHeartRate(day).collectLatest {
                _listHeartRate.postValue(it)
            }
        }
    }

    fun insertOxygen (oxygenEntity: OxygenEntity) {
        viewModelScope.launch (Dispatchers.IO) {
            healthParamUseCase.insertOxygen(oxygenEntity)
        }
    }

    fun getOxygen (day : String) {
        viewModelScope.launch {
            healthParamUseCase.getOxygen(day).collectLatest {
                _listOxygen.postValue(it)
            }
        }
    }

}