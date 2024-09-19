package com.example.tasktrackerapp.feature.presentation.screen.register.common

import com.example.tasktrackerapp.core.utils.UIText

data class PasswordFieldState(
    val value: String = "",
    val isValid: Boolean = true,
    val isValueVisible: Boolean = false,
    val message: UIText = UIText.DynamicString(""),
)