package com.example.tasktrackerapp.feature.presentation.screen.login.common

import com.example.tasktrackerapp.core.utils.UIText

data class LoginState(
    val isPasswordVisible: Boolean = false,
    val userNameValue: String = "",
    val passwordValue: String = "",
    val usernameMessage: UIText = UIText.DynamicString(""),
    val passwordMessage: UIText = UIText.DynamicString(""),
    val usernameIsValid: Boolean = true,
    val passwordIsValid: Boolean = true,
    val isLoading: Boolean = false,
)