package com.project.elderlyhealthcare.presentation.viewmodels.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
	private val _isLoading = MutableLiveData<Boolean?>()
	val isLoading: LiveData<Boolean?> = _isLoading

	private val messageFail = MutableLiveData<String?>()

	private val accessToken = MutableLiveData<String?>()

	private val remoteSuccess = MutableLiveData<Boolean?>()
	private val remoteRefreshTokenSuccess = MutableLiveData<Boolean?>()


	protected fun updateLoading(value: Boolean) {
		_isLoading.value = value
	}

	private fun updateAccessToken(value: String?) {
		accessToken.value = value
	}

	protected fun updateSuccess (boolean: Boolean) {
		remoteSuccess.value = boolean
	}

	private fun updateTokenSuccess (value : Boolean) {
		remoteRefreshTokenSuccess.value = value
	}













}