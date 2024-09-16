package com.example.tasktrackerapp.feature.presentation.screen.register

sealed class RegisterEvent {
    data object ChangePasswordVisibility : RegisterEvent()
    data object ChangeConfirmPasswordVisibility : RegisterEvent()
    data class FirstNameTextChanged(val value: String) : RegisterEvent()
    data class LastNameTextChanged(val value: String) : RegisterEvent()
    data class EmailTextChanged(val value: String) : RegisterEvent()
    data class UserNameTextChanged(val value: String) : RegisterEvent()
    data class PasswordTextChanged(val value: String) : RegisterEvent()
    data class ConfirmPasswordTextChanged(val value: String) : RegisterEvent()
    data object OnRegisterPress : RegisterEvent()
    data class SetBasicDialogVisibility(val visible: Boolean) : RegisterEvent()
}