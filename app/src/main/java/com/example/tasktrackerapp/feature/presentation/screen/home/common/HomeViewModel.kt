package com.example.tasktrackerapp.feature.presentation.screen.home.common

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasktrackerapp.feature.presentation.screen.home.navigation.BottomRoutes
import com.example.tasktrackerapp.feature.presentation.screen.login.common.LoginState
import com.example.tasktrackerapp.feature.presentation.screen.login.common.LoginViewModel.UIEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    fun onProjectClick() {
        viewModelScope.launch {
            if (_state.value.currentScreen != BottomRoutes.project) {
                _state.value = state.value.copy(currentScreen = BottomRoutes.project)
                _uiEvents.emit(UIEvents.GoToProject)
            }
        }
    }

    fun onProfileClick() {
        viewModelScope.launch {
            if (_state.value.currentScreen != BottomRoutes.profile) {
                _state.value = state.value.copy(currentScreen = BottomRoutes.profile)
                _uiEvents.emit(UIEvents.GoToProfile)
            }
        }
    }
}