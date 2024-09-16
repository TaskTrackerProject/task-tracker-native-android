package com.example.tasktrackerapp.feature.presentation.screen.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
    private val _state = mutableStateOf(LoginState())
    val state: State<LoginState> = _state

    fun onEvent(event: LoginEvent) {
        when(event) {
            is LoginEvent.ChangePasswordVisibility -> changePasswordVisibility()
            is LoginEvent.UserNameTextChanged -> setUserNameValue(event.value)
            is LoginEvent.PasswordTextChanged -> setPasswordValue(event.value)
        }
    }

    private fun changePasswordVisibility() {
        _state.value = state.value.copy(isPasswordVisible = !state.value.isPasswordVisible)
    }

    private fun setPasswordValue(data: String) {
        _state.value = state.value.copy(passwordValue = data)
    }

    private fun setUserNameValue(data: String) {
        _state.value = state.value.copy(userNameValue = data)
    }
}