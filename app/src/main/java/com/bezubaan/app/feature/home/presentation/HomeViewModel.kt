package com.bezubaan.app.feature.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bezubaan.app.feature.rescue.domain.model.RescueCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadNearbyCases()
    }

    private fun loadNearbyCases() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            delay(1000)
            val cases = listOf(
                RescueCase(id = "1", title = "Injured Stray Dog", location = "MG Road, Bangalore", status = "Reported"),
                RescueCase(id = "2", title = "Cat stuck in tree", location = "Indiranagar, Bangalore", status = "Rescued")
            )
            _uiState.update { it.copy(isLoading = false, nearbyCases = cases) }
        }
    }
}
