package com.example.tasktrackerapp.feature.presentation.screen.register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tasktrackerapp.core.model.Either
import com.example.tasktrackerapp.core.model.ResultModel
import com.example.tasktrackerapp.core.utils.Utility
import com.example.tasktrackerapp.feature.domain.model.UserModel
import com.example.tasktrackerapp.feature.domain.usecase.RegisterScreenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerScreenUseCase: RegisterScreenUseCase,
) : ViewModel() {
    private val _firstNameValue = mutableStateOf("")
    val firstNameValue: State<String> = _firstNameValue
    private val _isFirstNameValid = mutableStateOf(true)
    val isFirstNameValid: State<Boolean> = _isFirstNameValid
    private val _firstNameMessage = mutableStateOf("")
    val firstNameMessage: State<String> = _firstNameMessage

    private val _lastNameValue = mutableStateOf("")
    val lastNameValue: State<String> = _lastNameValue
    private val _isLastNameValid = mutableStateOf(true)
    val isLastNameValid: State<Boolean> = _isLastNameValid
    private val _lastNameMessage = mutableStateOf("")
    val lastNameMessage: State<String> = _lastNameMessage

    private val _emailValue = mutableStateOf("")
    val emailValue: State<String> = _emailValue
    private val _isEmailValid = mutableStateOf(true)
    val isEmailValid: State<Boolean> = _isEmailValid
    private val _emailMessage = mutableStateOf("")
    val emailMessage: State<String> = _emailMessage

    private val _usernameValue = mutableStateOf("")
    val usernameValue: State<String> = _usernameValue
    private val _isUserNameValid = mutableStateOf(true)
    val isUserNameValid: State<Boolean> = _isUserNameValid
    private val _usernameMessage = mutableStateOf("")
    val usernameMessage: State<String> = _usernameMessage

    private val _passwordValue = mutableStateOf("")
    val passwordValue: State<String> = _passwordValue
    private val _isPasswordValid = mutableStateOf(true)
    val isPasswordValid: State<Boolean> = _isPasswordValid
    private val _isPasswordVisible = mutableStateOf(false)
    val isPasswordVisible: State<Boolean> = _isPasswordVisible
    private val _passwordMessage = mutableStateOf("")
    val passwordMessage: State<String> = _passwordMessage

    private val _confirmPasswordValue = mutableStateOf("")
    val confirmPasswordValue: State<String> = _confirmPasswordValue
    private val _isConfirmPasswordValid = mutableStateOf(true)
    val isConfirmPasswordValid: State<Boolean> = _isConfirmPasswordValid
    private val _isConfirmPasswordVisible = mutableStateOf(false)
    val isConfirmPasswordVisible: State<Boolean> = _isConfirmPasswordVisible
    private val _confirmPasswordMessage = mutableStateOf("")
    val confirmPasswordMessage: State<String> = _confirmPasswordMessage

    private val _dialogMessage = mutableStateOf("")
    val dialogMessage: State<String> = _dialogMessage
    private val _showLoading = mutableStateOf(false)
    val showLoading: State<Boolean> = _showLoading
    private val _showBasicDialog = mutableStateOf(false)
    val showBasicDialog: State<Boolean> = _showBasicDialog

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
        _isConfirmPasswordVisible.value = !isConfirmPasswordVisible.value
    }

    private fun changePasswordVisibility() {
        _isPasswordVisible.value = !isPasswordVisible.value
    }

    private fun setConfirmPasswordValue(data: String) {
        _confirmPasswordValue.value = data
        _isConfirmPasswordValid.value = true
    }

    private fun setEmailValue(data: String) {
        _emailValue.value = data
        _isEmailValid.value = true
    }

    private fun setFirstNameValue(data: String) {
        _firstNameValue.value = data
        _isFirstNameValid.value = true
    }

    private fun setLastNameValue(data: String) {
        _lastNameValue.value = data
        _isLastNameValid.value = true
    }

    private fun setPasswordValue(data: String) {
        _passwordValue.value = data
        _isPasswordValid.value = true
    }

    private fun setUserNameValue(data: String) {
        _usernameValue.value = data
        _isUserNameValid.value = true
    }

    private fun setBasicDialogState(isVisible: Boolean) {
        _showBasicDialog.value = isVisible
    }

    private fun onRegister() {
        val result = fieldValidations()
        if (result.isSuccess) {
            viewModelScope.launch {
                _showLoading.value = true
                when(val registerResult = registerScreenUseCase.registerUser(result.data!!)) {
                    is Either.Left -> {
                        _showLoading.value = false
                        _showBasicDialog.value = true
                        _dialogMessage.value = registerResult.value
                    }
                    is Either.Right -> {
                        _showLoading.value = false
                        _showBasicDialog.value = true
                        _dialogMessage.value = registerResult.value.message
                    }
                }
            }
        }
    }

    private fun fieldValidations(): ResultModel<UserModel> {
        var isValidated = true
        val firstName = Utility.capitalizedInitialLetter(_firstNameValue.value.trim())
        val lastName = Utility.capitalizedInitialLetter(_lastNameValue.value.trim())
        val email = _emailValue.value.trim()
        val userName = _usernameValue.value.trim()
        val password = _passwordValue.value.trim()
        val confirmPassword = _confirmPasswordValue.value.trim()

        if (firstName.isBlank() || firstName.isEmpty()) {
            isValidated = false
            _isFirstNameValid.value = false
            _firstNameMessage.value = "Field is empty"
        } else {
            _isFirstNameValid.value = true
        }

        if (lastName.isBlank() || lastName.isEmpty()) {
            isValidated = false
            _isLastNameValid.value = false
            _lastNameMessage.value = "Field is empty"
        } else {
            _isLastNameValid.value = true
        }

        if (email.isBlank() || email.isEmpty()) {
            isValidated = false
            _isEmailValid.value = false
            _emailMessage.value = "Field is empty"
        } else if (!Utility.isValidEmail(email)) {
            isValidated = false
            _isEmailValid.value = false
            _emailMessage.value = "Enter a valid email address"
        } else {
            _isEmailValid.value = true
        }

        if (userName.isBlank() || userName.isEmpty()) {
            isValidated = false
            _isUserNameValid.value = false
            _usernameMessage.value = "Field is empty"
        } else {
            _isUserNameValid.value = true
        }

        if (password.isBlank() || password.isEmpty()) {
            isValidated = false
            _isPasswordValid.value = false
            _passwordMessage.value = "Field is empty"
        } else if (password.length < 7) {
            isValidated = false
            _isPasswordValid.value = false
            _passwordMessage.value = "Password must be minimum of 7 characters"
        } else if (!password.any { it.isLowerCase() }) {
            isValidated = false
            _isPasswordValid.value = false
            _passwordMessage.value = "Password must include at least 1 lowercase letter"
        } else if (!password.any { it.isUpperCase() }) {
            isValidated = false
            _isPasswordValid.value = false
            _passwordMessage.value = "Password must include at least 1 uppercase letter"
        } else if (!password.any { it.isDigit() }) {
            isValidated = false
            _isPasswordValid.value = false
            _passwordMessage.value = "Password must include at least 1 number"
        } else if (password.all { it.isLetterOrDigit() }) {
            isValidated = false
            _isPasswordValid.value = false
            _passwordMessage.value = "Password must include at least 1 special character"
        } else {
            _isPasswordValid.value = true
        }

        if (confirmPassword.isBlank() || confirmPassword.isEmpty()) {
            isValidated = false
            _isConfirmPasswordValid.value = false
            _confirmPasswordMessage.value = "Field is empty"
        } else if (confirmPassword != password) {
            isValidated = false
            _isConfirmPasswordValid.value = false
            _confirmPasswordMessage.value = "Confirm Password does not matched"
        } else {
            _isConfirmPasswordValid.value = true
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
        }
        else {
            return ResultModel(
                isSuccess = false,
            )
        }
    }
}