package com.bezubaan.app.feature.rescue.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bezubaan.app.core.common.Resource
import com.bezubaan.app.feature.rescue.domain.model.RescueCase
import com.bezubaan.app.feature.rescue.domain.repository.RescueRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RescueTrackingViewModel @Inject constructor(
    private val rescueRepository: RescueRepository
) : ViewModel() {

    private val _rescueCase = MutableStateFlow<RescueCase?>(null)
    val rescueCase: StateFlow<RescueCase?> = _rescueCase.asStateFlow()

    private var pollingJob: Job? = null

    fun startTracking(rescueId: String) {
        if (pollingJob?.isActive == true) return

        pollingJob = viewModelScope.launch {
            while (true) {
                rescueRepository.getRescueCaseById(rescueId).collect { result ->
                    if (result is Resource.Success) {
                        _rescueCase.value = result.data
                    }
                }
                delay(10000) // Poll every 10 seconds
            }
        }
    }

    fun stopTracking() {
        pollingJob?.cancel()
        pollingJob = null
    }

    override fun onCleared() {
        super.onCleared()
        stopTracking()
    }
}
