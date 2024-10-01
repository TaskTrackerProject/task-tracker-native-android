package com.example.tasktrackerapp.feature.presentation.screen.register.common

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasktrackerapp.core.model.Either
import com.example.tasktrackerapp.core.utils.UIText
import com.example.tasktrackerapp.feature.domain.model.UserModel
import com.example.tasktrackerapp.feature.domain.usecase.register.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.tasktrackerapp.core.constants.AppConstants
import com.example.tasktrackerapp.core.model.ResultModel
import com.example.tasktrackerapp.feature.domain.model.verification.VerificationParamModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
) : ViewModel() {

    sealed class UIEvents {
        data class GoToVerificationPage(val data: String) : UIEvents()
        data class ShowSuccessSnackBar(val message: UIText) : UIEvents()
        data class ShowFailedSnackBar(val message: UIText) : UIEvents()
    }

    private val _firstNameState = mutableStateOf(CommonFieldState())
    val firstNameState: State<CommonFieldState> = _firstNameState

    private val _lastNameState = mutableStateOf(CommonFieldState())
    val lastNameState: State<CommonFieldState> = _lastNameState

    private val _emailState = mutableStateOf(CommonFieldState())
    val emailState: State<CommonFieldState> = _emailState

    private val _usernameState = mutableStateOf(CommonFieldState())
    val usernameState: State<CommonFieldState> = _usernameState

    private val _passwordState = mutableStateOf(PasswordFieldState())
    val passwordState: State<PasswordFieldState> = _passwordState

    private val _confirmPasswordState = mutableStateOf(PasswordFieldState())
    val confirmPasswordState: State<PasswordFieldState> = _confirmPasswordState

    private val _showLoading = mutableStateOf(false)
    val showLoading: State<Boolean> = _showLoading

    private val _dialogState = mutableStateOf(DialogState())
    val dialogState: State<DialogState> = _dialogState

    private val _uiEvents = MutableSharedFlow<UIEvents>()
    val uiEvents = _uiEvents.asSharedFlow()

    fun changeConfirmPasswordVisibility() {
        _confirmPasswordState.value = confirmPasswordState.value.copy(
            isValueVisible = !confirmPasswordState.value.isValueVisible
        )
    }

    fun changePasswordVisibility() {
        _passwordState.value = passwordState.value.copy(
            isValueVisible = !passwordState.value.isValueVisible,
        )
    }

    fun setConfirmPasswordValue(data: String) {
        val maxLength = AppConstants.USERNAME_MAX_LEN
        val text = if (data.length <= maxLength) {
            data
        } else {
            data.take(maxLength)
        }
        _confirmPasswordState.value = confirmPasswordState.value.copy(
            value = text,
            isValid = true,
        )
    }

    fun setEmailValue(data: String) {
        _emailState.value = emailState.value.copy(
            value = data,
            isValid = true,
        )
    }

    fun setFirstNameValue(data: String) {
        val text = data.filter { it.isLetter() || it.isWhitespace() }
        _firstNameState.value = firstNameState.value.copy(
            value = text,
            isValid = true,
        )
    }

    fun setLastNameValue(data: String) {
        val text = data.filter { it.isLetter() || it.isWhitespace() }
        _lastNameState.value = lastNameState.value.copy(
            value = text,
            isValid = true,
        )
    }

    fun setPasswordValue(data: String) {
        val maxLength = AppConstants.PASSWORD_MAX_LEN
        val text = if (data.length <= maxLength) {
            data
        } else {
            data.take(maxLength)
        }
        _passwordState.value = passwordState.value.copy(
            value = text,
            isValid = true,
        )
    }

    fun setUserNameValue(data: String) {
        val maxLength = AppConstants.USERNAME_MAX_LEN
        val filteredText = data.filter { it.isLetterOrDigit() }
        val text = if (filteredText.length <= maxLength) {
            filteredText
        } else {
            filteredText.take(maxLength)
        }
        _usernameState.value = usernameState.value.copy(
            value = text,
            isValid = true,
        )
    }

    fun setBasicDialogState(isVisible: Boolean) {
        _dialogState.value = dialogState.value.copy(
            showDialog = isVisible,
        )
    }

    private fun validate(): ResultModel<Any> {
        var isSuccess = true

        val firstNameResult =
            registerUseCase.validateEmptyField(_firstNameState.value.value)
        if (!firstNameResult.isSuccess) {
            isSuccess = false
            _firstNameState.value = firstNameState.value.copy(
                isValid = false,
                message = firstNameResult.message,
            )
        } else {
            _firstNameState.value = firstNameState.value.copy(
                isValid = true,
            )
        }

        val lastNameResult =
            registerUseCase.validateEmptyField(_lastNameState.value.value)
        if (!lastNameResult.isSuccess) {
            isSuccess = false
            _lastNameState.value = lastNameState.value.copy(
                isValid = false,
                message = lastNameResult.message,
            )
        } else {
            _lastNameState.value = lastNameState.value.copy(
                isValid = true,
            )
        }

        val emailResult = registerUseCase.validateEmail(_emailState.value.value)
        if (!emailResult.isSuccess) {
            isSuccess = false
            _emailState.value = emailState.value.copy(
                isValid = false,
                message = emailResult.message,
            )
        } else {
            _emailState.value = emailState.value.copy(
                isValid = true,
            )
        }

        val usernameResult =
            registerUseCase.validateEmptyField(_usernameState.value.value)
        if (!usernameResult.isSuccess) {
            isSuccess = false
            _usernameState.value = usernameState.value.copy(
                isValid = false,
                message = usernameResult.message,
            )
        } else {
            _usernameState.value = usernameState.value.copy(
                isValid = true,
            )
        }

        val passwordResult = registerUseCase.validatePassword(_passwordState.value.value)
        if (!passwordResult.isSuccess) {
            isSuccess = false
            _passwordState.value = passwordState.value.copy(
                isValid = false,
                message = passwordResult.message,
            )
        } else {
            _passwordState.value = passwordState.value.copy(
                isValid = true,
            )
        }

        val confirmPasswordResult =
            registerUseCase.validateConfirmPassword(
                _passwordState.value.value,
                _confirmPasswordState.value.value,
            )
        if (!confirmPasswordResult.isSuccess) {
            isSuccess = false
            _confirmPasswordState.value = confirmPasswordState.value.copy(
                isValid = false,
                message = confirmPasswordResult.message,
            )
        } else {
            _confirmPasswordState.value = confirmPasswordState.value.copy(
                isValid = true,
            )
        }

        return ResultModel(isSuccess = isSuccess)
    }

    fun onRegister() {
        viewModelScope.launch {
            _showLoading.value = true
            if (validate().isSuccess) {
                val param = UserModel(
                    firstName = _firstNameState.value.value,
                    lastName = _lastNameState.value.value,
                    email = _emailState.value.value,
                    userName = _usernameState.value.value,
                    password = _passwordState.value.value,
                )
                when (val registerResult = registerUseCase.registerUser(param)) {
                    is Either.Left -> {
                        _showLoading.value = false
                        _uiEvents.emit(UIEvents.ShowFailedSnackBar(registerResult.value));
                    }

                    is Either.Right -> {
                        _showLoading.value = false
                        _uiEvents.emit(UIEvents.ShowSuccessSnackBar(registerResult.value.message))

                        val id = registerResult.value.data ?: ""
                        val data = registerUseCase.toJson(
                            VerificationParamModel(
                                id = id,
                                email = _emailState.value.value,
                            ),
                        )
                        _uiEvents.emit(UIEvents.GoToVerificationPage(data))
                    }
                }
            } else {
                _showLoading.value = false
            }
        }
    }
}