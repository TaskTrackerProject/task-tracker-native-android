package com.example.tasktrackerapp.feature.presentation.screen.splash.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasktrackerapp.core.constants.PrefConstants
import com.example.tasktrackerapp.core.utils.UIText
import com.example.tasktrackerapp.feature.domain.usecase.splash.SplashUseCase
import com.example.tasktrackerapp.feature.presentation.screen.register.common.RegisterViewModel.UIEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val useCase: SplashUseCase,
) : ViewModel() {

    sealed class UIEvents {
        data object GoToLogin : UIEvents()
        data object GoToHome : UIEvents()
    }

    private val _uiEvents = MutableSharedFlow<UIEvents>()
    val uiEvents = _uiEvents.asSharedFlow()

    init {
        viewModelScope.launch {
            delay(2000)
            val userId = useCase.getPrefData(PrefConstants.USER_ID_KEY)
            if (!userId.isNullOrEmpty()) {
                _uiEvents.emit(UIEvents.GoToHome)
            } else {
                _uiEvents.emit(UIEvents.GoToLogin)
            }
        }
    }
}