package com.example.tasktrackerapp.feature.presentation.screen.login.common

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasktrackerapp.core.utils.UIText
import com.example.tasktrackerapp.feature.domain.model.verification.VerificationParamModel
import com.example.tasktrackerapp.feature.domain.usecase.login.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val useCase: LoginUseCase,
) : ViewModel() {
    sealed class UIEvents {
        data object GoToHome : UIEvents()
        data class GoToVerification(val data: String) : UIEvents()
        data class ShowSnackBar(val message: UIText) : UIEvents()
        data class ShowToast(val message: UIText) : UIEvents()
    }

    private val _state = mutableStateOf(LoginState())
    val state: State<LoginState> = _state

    private val _uiEvents = MutableSharedFlow<UIEvents>()
    val uiEvents = _uiEvents.asSharedFlow()

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

        viewModelScope.launch {
            _state.value = state.value.copy(
                isLoading = true,
            )
            val result = useCase.login(username, password)
            _state.value = state.value.copy(
                isLoading = false,
            )
            if (result.isSuccess) {
                _uiEvents.emit(UIEvents.ShowToast(result.message))
                delay(1000)
                _uiEvents.emit(UIEvents.GoToHome)
            } else if (result.isNotVerified) {
                _uiEvents.emit(UIEvents.ShowToast(result.message))
                delay(1000)
                val id = result.id
                val email = result.email
                val data = useCase.toJson(
                    VerificationParamModel(
                        id = id,
                        email = email,
                    ),
                )
                _uiEvents.emit(UIEvents.GoToVerification(data))
            } else {
                _uiEvents.emit(UIEvents.ShowSnackBar(result.message))
            }
        }
    }
}