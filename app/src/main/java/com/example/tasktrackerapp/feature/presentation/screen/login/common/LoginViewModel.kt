package com.example.tasktrackerapp.feature.presentation.screen.login.common

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.tasktrackerapp.feature.domain.usecase.login.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val useCase: LoginUseCase,
) : ViewModel() {
    private val _state = mutableStateOf(LoginState())
    val state: State<LoginState> = _state

    fun changePasswordVisibility() {
        _state.value = state.value.copy(isPasswordVisible = !state.value.isPasswordVisible)
    }

    fun setPasswordValue(data: String) {
        _state.value = state.value.copy(
            passwordValue = data,
            passwordIsValid = true,
        )
    }

    fun setUserNameValue(data: String) {
        _state.value = state.value.copy(
            userNameValue = data,
            usernameIsValid = true,
        )
    }

    fun onLogin() {
        val username = _state.value.userNameValue
        val password = _state.value.passwordValue
        var hasError = false

        val usernameValidateResult = useCase.validateEmptyField(username)
        if (!usernameValidateResult.isSuccess) {
            hasError = true
            _state.value = state.value.copy(
                usernameIsValid = false,
                usernameMessage = usernameValidateResult.message,
            )
        }

        val passwordValidateResult = useCase.validateEmptyField(password)
        if (!passwordValidateResult.isSuccess) {
            hasError = true
            _state.value = state.value.copy(
                passwordIsValid = false,
                passwordMessage = passwordValidateResult.message,
            )
        }

        if (hasError) {
            return
        }
    }
}