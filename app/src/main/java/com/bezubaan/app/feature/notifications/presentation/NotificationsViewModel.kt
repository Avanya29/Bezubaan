package com.bezubaan.app.feature.notifications.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bezubaan.app.core.common.Resource
import com.bezubaan.app.feature.notifications.domain.model.Notification
import com.bezubaan.app.feature.notifications.domain.repository.NotificationsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NotificationsState(
    val isLoading: Boolean = false,
    val notifications: List<Notification> = emptyList(),
    val error: String? = null,
    val filter: String = "ALL" // e.g. "ALL", "RESCUE", "MEDICAL", etc
)

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val repository: NotificationsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(NotificationsState())
    val state: StateFlow<NotificationsState> = _state.asStateFlow()

    init {
        fetchNotifications()
    }

    fun fetchNotifications() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            when (val result = repository.getUserNotifications()) {
                is Resource.Success -> {
                    _state.update { 
                        it.copy(
                            isLoading = false,
                            notifications = result.data ?: emptyList()
                        )
                    }
                }
                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = result.message ?: "Unknown error"
                        )
                    }
                }
                is Resource.Loading -> {
                    _state.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    fun markAsRead(id: String) {
        viewModelScope.launch {
            repository.markAsRead(id)
            // Optimistically update UI
            _state.update { currentState ->
                currentState.copy(
                    notifications = currentState.notifications.map {
                        if (it.id == id) it.copy(isRead = true) else it
                    }
                )
            }
        }
    }

    fun markAllAsRead() {
        viewModelScope.launch {
            val unreadIds = _state.value.notifications.filter { !it.isRead }.map { it.id }
            unreadIds.forEach { id ->
                repository.markAsRead(id)
            }
            // Optimistically update UI
            _state.update { currentState ->
                currentState.copy(
                    notifications = currentState.notifications.map { it.copy(isRead = true) }
                )
            }
        }
    }

    fun setFilter(filter: String) {
        _state.update { it.copy(filter = filter) }
    }
}
