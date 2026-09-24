package com.bezubaan.app.feature.rescue.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bezubaan.app.core.common.Resource
import com.bezubaan.app.feature.rescue.domain.model.RescueCase
import com.bezubaan.app.feature.rescue.domain.repository.RescueRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RescueCaseDetailsState(
    val rescueCase: RescueCase? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class RescueCaseDetailsViewModel @Inject constructor(
    private val repository: RescueRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(RescueCaseDetailsState())
    val state: StateFlow<RescueCaseDetailsState> = _state.asStateFlow()

    init {
        val rescueId = savedStateHandle.get<String>("rescueId")
        if (rescueId != null) {
            fetchRescueCase(rescueId)
        } else {
            _state.update { it.copy(error = "No rescue ID provided") }
        }
    }

    private fun fetchRescueCase(id: String) {
        viewModelScope.launch {
            repository.getRescueCaseById(id).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                rescueCase = result.data,
                                isLoading = false,
                                error = null
                            )
                        }
                    }
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = result.message ?: "An unexpected error occurred"
                            )
                        }
                    }
                    is Resource.Loading -> {
                        _state.update { it.copy(isLoading = true) }
                    }
                }
            }
        }
    }
}
