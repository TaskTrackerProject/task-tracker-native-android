package com.example.tasktrackerapp.feature.presentation.screen.login

sealed class LoginEvent {
    data object ChangePasswordVisibility : LoginEvent()
    data class UserNameTextChanged(val value: String) : LoginEvent()
    data class PasswordTextChanged(val value: String) : LoginEvent()
}