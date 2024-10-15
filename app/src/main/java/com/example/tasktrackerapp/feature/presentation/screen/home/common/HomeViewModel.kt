package com.example.tasktrackerapp.feature.presentation.screen.home.common

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    sealed class UIEvents {
        data object GoToProject : UIEvents()
        data object GoToProfile : UIEvents()
    }

    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    private val _uiEvents = MutableSharedFlow<UIEvents>()
    val uiEvents = _uiEvents.asSharedFlow()

    private fun setSelectedTab(
        isProjectSelected: Boolean = false,
        isProfileSelected: Boolean = false,
    ) {
        _state.value = state.value.copy(
            isProjectSelected = isProjectSelected,
            isProfileSelected = isProfileSelected,
        )
    }

    fun onProjectClick() {
        viewModelScope.launch {
            if (!_state.value.isProjectSelected) {
                setSelectedTab(isProjectSelected = true)
                _uiEvents.emit(UIEvents.GoToProject)
            }
        }
    }

    fun onProfileClick() {
        viewModelScope.launch {
            if (!_state.value.isProfileSelected) {
                setSelectedTab(isProfileSelected = true)
                _uiEvents.emit(UIEvents.GoToProfile)
            }
        }
    }
}