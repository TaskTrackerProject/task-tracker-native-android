package com.example.tasktrackerapp.feature.presentation.screen.register

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

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
) : ViewModel() {
    private val _state = mutableStateOf(RegisterState())
    val state: State<RegisterState> = _state

    fun onEvent(event: RegisterEvent) {
        when (event) {
            RegisterEvent.ChangeConfirmPasswordVisibility -> changeConfirmPasswordVisibility()
            RegisterEvent.ChangePasswordVisibility -> changePasswordVisibility()
            is RegisterEvent.ConfirmPasswordTextChanged -> setConfirmPasswordValue(event.value)
            is RegisterEvent.EmailTextChanged -> setEmailValue(event.value)
            is RegisterEvent.FirstNameTextChanged -> setFirstNameValue(event.value)
            is RegisterEvent.LastNameTextChanged -> setLastNameValue(event.value)
            RegisterEvent.OnRegisterPress -> onRegister()
            is RegisterEvent.PasswordTextChanged -> setPasswordValue(event.value)
            is RegisterEvent.UserNameTextChanged -> setUserNameValue(event.value)
            is RegisterEvent.SetBasicDialogVisibility -> setBasicDialogState(event.visible)
        }
    }

    private fun changeConfirmPasswordVisibility() {
        val isVisible = !state.value.isConfirmPasswordVisible
        _state.value = state.value.copy(
            isConfirmPasswordVisible = isVisible
        )
    }

    private fun changePasswordVisibility() {
        val isVisible = !state.value.isPasswordVisible
        _state.value = state.value.copy(
            isPasswordVisible = isVisible,
        )
    }

    private fun setConfirmPasswordValue(data: String) {
        val maxLength = AppConstants.USERNAME_MAX_LEN
        val filteredText = data.filter { it.isLetterOrDigit() }
        val text = if (filteredText.length <= maxLength) {
            filteredText
        } else {
            filteredText.take(maxLength)
        }
        _state.value = state.value.copy(
            confirmPasswordValue = text,
            isConfirmPasswordValid = true,
        )
    }

    private fun setEmailValue(data: String) {
        _state.value = state.value.copy(
            emailValue = data,
            isEmailValid = true,
        )
    }

    private fun setFirstNameValue(data: String) {
        val text = data.filter { it.isLetter() || it.isWhitespace() }
        _state.value = state.value.copy(
            firstNameValue = text,
            isFirstNameValid = true,
        )
    }

    private fun setLastNameValue(data: String) {
        val text = data.filter { it.isLetter() || it.isWhitespace() }
        _state.value = state.value.copy(
            lastNameValue = text,
            isLastNameValid = true,
        )
    }

    private fun setPasswordValue(data: String) {
        val maxLength = AppConstants.PASSWORD_MAX_LEN
        val filteredText = data.filter { it.isLetterOrDigit() }
        val text = if (filteredText.length <= maxLength) {
            filteredText
        } else {
            filteredText.take(maxLength)
        }
        _state.value = state.value.copy(
            passwordValue = text,
            isPasswordValid = true,
        )
    }

    private fun setUserNameValue(data: String) {
        val maxLength = AppConstants.USERNAME_MAX_LEN
        val filteredText = data.filter { it.isLetterOrDigit() }
        val text = if (filteredText.length <= maxLength) {
            filteredText
        } else {
            filteredText.take(maxLength)
        }
        _state.value = state.value.copy(
            usernameValue = text,
            isUserNameValid = true,
        )
    }

    private fun setBasicDialogState(isVisible: Boolean) {
        _state.value = state.value.copy(
            showBasicDialog = isVisible,
        )
    }

    private fun validate(): ResultModel<Any> {
        var isSuccess = true

        val firstNameResult =
            registerUseCase.validateEmptyField(_state.value.firstNameValue)
        if (!firstNameResult.isSuccess) {
            isSuccess = false
            _state.value = state.value.copy(
                isFirstNameValid = false,
                firstNameMessage = firstNameResult.message,
            )
        } else {
            _state.value = state.value.copy(
                isFirstNameValid = true
            )
        }

        val lastNameResult =
            registerUseCase.validateEmptyField(_state.value.lastNameValue)
        if (!lastNameResult.isSuccess) {
            isSuccess = false
            _state.value = state.value.copy(
                isLastNameValid = false,
                lastNameMessage = lastNameResult.message,
            )
        } else {
            _state.value = state.value.copy(
                isLastNameValid = true
            )
        }

        val emailResult = registerUseCase.validateEmail(_state.value.emailValue)
        if (!emailResult.isSuccess) {
            isSuccess = false
            _state.value = state.value.copy(
                isEmailValid = false,
                emailMessage = emailResult.message,
            )
        } else {
            _state.value = state.value.copy(
                isEmailValid = true
            )
        }

        val usernameResult =
            registerUseCase.validateEmptyField(_state.value.usernameValue)
        if (!usernameResult.isSuccess) {
            isSuccess = false
            _state.value = state.value.copy(
                isUserNameValid = false,
                usernameMessage = usernameResult.message,
            )
        } else {
            _state.value = state.value.copy(
                isUserNameValid = true
            )
        }

        val passwordResult = registerUseCase.validatePassword(_state.value.passwordValue)
        if (!passwordResult.isSuccess) {
            isSuccess = false
            _state.value = state.value.copy(
                isPasswordValid = false,
                passwordMessage = passwordResult.message,
            )
        } else {
            _state.value = state.value.copy(
                isPasswordValid = true
            )
        }

        val confirmPasswordResult =
            registerUseCase.validateConfirmPassword(
                _state.value.passwordValue,
                _state.value.confirmPasswordValue,
            )
        if (!confirmPasswordResult.isSuccess) {
            isSuccess = false
            _state.value = state.value.copy(
                isConfirmPasswordValid = false,
                confirmPasswordMessage = confirmPasswordResult.message,
            )
        } else {
            _state.value = state.value.copy(
                isConfirmPasswordValid = true
            )
        }

        return ResultModel(isSuccess = isSuccess)
    }

    private fun onRegister() {
        viewModelScope.launch {
            _state.value = state.value.copy(
                showLoading = true,
            )

            if (validate().isSuccess) {
                val param = UserModel(
                    firstName = _state.value.firstNameValue,
                    lastName = _state.value.lastNameValue,
                    email = _state.value.emailValue,
                    userName = _state.value.usernameValue,
                    password = _state.value.passwordValue,
                )
                when (val registerResult = registerUseCase.registerUser(param)) {
                    is Either.Left -> {
                        _state.value = state.value.copy(
                            showLoading = false,
                            showBasicDialog = true,
                            dialogMessage = UIText.DynamicString(registerResult.value),
                        )
                    }

                    is Either.Right -> {
                        _state.value = state.value.copy(
                            showLoading = false,
                            showBasicDialog = true,
                            dialogMessage = registerResult.value.message,
                        )
                    }
                }
            } else {
                _state.value = state.value.copy(
                    showLoading = false,
                )
            }
        }
    }
}