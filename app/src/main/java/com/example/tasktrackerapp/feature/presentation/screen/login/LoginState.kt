package com.example.tasktrackerapp.feature.presentation.screen.login

data class LoginState(
    val isPasswordVisible: Boolean = false,
    val userNameValue: String = "",
    val passwordValue: String = "",
)