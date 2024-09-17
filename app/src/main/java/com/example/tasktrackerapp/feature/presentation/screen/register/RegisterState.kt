package com.example.tasktrackerapp.feature.presentation.screen.register

import com.example.tasktrackerapp.core.utils.UIText

data class RegisterState(
    val firstNameValue: String = "",
    val isFirstNameValid: Boolean = true,
    val firstNameMessage: UIText = UIText.DynamicString(""),
    val lastNameValue: String = "",
    val isLastNameValid: Boolean = true,
    val lastNameMessage: UIText = UIText.DynamicString(""),
    val emailValue: String = "",
    val isEmailValid: Boolean = true,
    val emailMessage: UIText = UIText.DynamicString(""),
    val usernameValue: String = "",
    val isUserNameValid: Boolean = true,
    val usernameMessage: UIText = UIText.DynamicString(""),
    val passwordValue: String = "",
    val isPasswordValid: Boolean = true,
    val isPasswordVisible: Boolean = false,
    val passwordMessage: UIText = UIText.DynamicString(""),
    val confirmPasswordValue: String = "",
    val isConfirmPasswordValid: Boolean = true,
    val isConfirmPasswordVisible: Boolean = false,
    val confirmPasswordMessage: UIText = UIText.DynamicString(""),
    val dialogMessage: UIText = UIText.DynamicString(""),
    val showLoading: Boolean = false,
    val showBasicDialog: Boolean = false,
)