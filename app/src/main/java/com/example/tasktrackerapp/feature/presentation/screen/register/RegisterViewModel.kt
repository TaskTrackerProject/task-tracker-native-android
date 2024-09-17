package com.example.tasktrackerapp.feature.presentation.screen.register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasktrackerapp.core.model.Either
import com.example.tasktrackerapp.core.model.ResultModel
import com.example.tasktrackerapp.core.utils.UIText
import com.example.tasktrackerapp.core.utils.Utility
import com.example.tasktrackerapp.feature.domain.model.UserModel
import com.example.tasktrackerapp.feature.domain.usecase.RegisterScreenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.tasktrackerapp.R
import com.example.tasktrackerapp.core.constants.AppConstants

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerScreenUseCase: RegisterScreenUseCase,
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
        _state.value = state.value.copy(
            confirmPasswordValue = data,
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
        _state.value = state.value.copy(
            firstNameValue = data,
            isFirstNameValid = true,
        )
    }

    private fun setLastNameValue(data: String) {
        _state.value = state.value.copy(
            lastNameValue = data,
            isLastNameValid = true,
        )
    }

    private fun setPasswordValue(data: String) {
        _state.value = state.value.copy(
            passwordValue = data,
            isPasswordValid = true,
        )
    }

    private fun setUserNameValue(data: String) {
        _state.value = state.value.copy(
            usernameValue = data,
            isUserNameValid = true,
        )
    }

    private fun setBasicDialogState(isVisible: Boolean) {
        _state.value = state.value.copy(
            showBasicDialog = isVisible,
        )
    }

    private fun onRegister() {
        val result = fieldValidations()
        if (result.isSuccess) {
            viewModelScope.launch {
                _state.value = state.value.copy(
                    showLoading = true,
                )
                when (val registerResult = registerScreenUseCase.registerUser(result.data!!)) {
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
                            dialogMessage = UIText.DynamicString(registerResult.value.message),
                        )
                    }
                }
            }
        }
    }

    private fun fieldValidations(): ResultModel<UserModel> {
        var isValidated = true
        val firstName = Utility.capitalizedInitialLetter(_state.value.firstNameValue.trim())
        val lastName = Utility.capitalizedInitialLetter(_state.value.lastNameValue.trim())
        val email = _state.value.emailValue.trim()
        val userName = _state.value.usernameValue.trim()
        val password = _state.value.passwordValue.trim()
        val confirmPassword = _state.value.confirmPasswordValue.trim()

        if (firstName.isBlank() || firstName.isEmpty()) {
            isValidated = false
            _state.value = state.value.copy(
                isFirstNameValid = false,
                firstNameMessage = UIText.StringResource(R.string.field_is_empty),
            )
        } else {
            _state.value = state.value.copy(
                isFirstNameValid = true,
            )
        }

        if (lastName.isBlank() || lastName.isEmpty()) {
            isValidated = false
            _state.value = state.value.copy(
                isLastNameValid = false,
                lastNameMessage = UIText.StringResource(R.string.field_is_empty),
            )
        } else {
            _state.value = state.value.copy(
                isLastNameValid = true,
            )
        }

        if (email.isBlank() || email.isEmpty()) {
            isValidated = false
            _state.value = state.value.copy(
                isEmailValid = false,
                emailMessage = UIText.StringResource(R.string.field_is_empty),
            )
        } else if (!Utility.isValidEmail(email)) {
            isValidated = false
            _state.value = state.value.copy(
                isEmailValid = false,
                emailMessage = UIText.StringResource(R.string.enter_valid_email_add),
            )
        } else {
            _state.value = state.value.copy(
                isEmailValid = true,
            )
        }

        if (userName.isBlank() || userName.isEmpty()) {
            isValidated = false
            _state.value = state.value.copy(
                isUserNameValid = false,
                usernameMessage = UIText.StringResource(R.string.field_is_empty),
            )
        } else {
            _state.value = state.value.copy(
                isUserNameValid = true,
            )
        }

        if (password.isBlank() || password.isEmpty()) {
            isValidated = false
            _state.value = state.value.copy(
                isPasswordValid = false,
                passwordMessage = UIText.StringResource(R.string.field_is_empty),
            )
        } else if (password.length < 7) {
            isValidated = false
            _state.value = state.value.copy(
                isPasswordValid = false,
                passwordMessage = UIText.StringResource(
                    R.string.password_min_char,
                    AppConstants.PASSWORD_MIN_LEN,
                ),
            )
        } else if (!password.any { it.isLowerCase() }) {
            isValidated = false
            _state.value = state.value.copy(
                isPasswordValid = false,
                passwordMessage = UIText.StringResource(R.string.password_include_lowercase),
            )
        } else if (!password.any { it.isUpperCase() }) {
            isValidated = false
            _state.value = state.value.copy(
                isPasswordValid = false,
                passwordMessage = UIText.StringResource(R.string.password_include_uppercase),
            )
        } else if (!password.any { it.isDigit() }) {
            isValidated = false
            _state.value = state.value.copy(
                isPasswordValid = false,
                passwordMessage = UIText.StringResource(R.string.password_include_number),
            )
        } else if (password.all { it.isLetterOrDigit() }) {
            isValidated = false
            _state.value = state.value.copy(
                isPasswordValid = false,
                passwordMessage = UIText.StringResource(R.string.password_include_special_char),
            )
        } else {
            _state.value = state.value.copy(
                isPasswordValid = true,
            )
        }

        if (confirmPassword.isBlank() || confirmPassword.isEmpty()) {
            isValidated = false
            _state.value = state.value.copy(
                isConfirmPasswordValid = false,
                confirmPasswordMessage = UIText.StringResource(R.string.field_is_empty),
            )
        } else if (confirmPassword != password) {
            isValidated = false
            _state.value = state.value.copy(
                isConfirmPasswordValid = false,
                confirmPasswordMessage = UIText.StringResource(R.string.confirm_pass_not_match),
            )
        } else {
            _state.value = state.value.copy(
                isConfirmPasswordValid = true,
            )
        }

        if (isValidated) {
            return ResultModel(
                isSuccess = true,
                data = UserModel(
                    firstName = firstName,
                    lastName = lastName,
                    email = email,
                    userName = userName,
                    password = password,
                )
            )
        } else {
            return ResultModel(
                isSuccess = false,
            )
        }
    }
}