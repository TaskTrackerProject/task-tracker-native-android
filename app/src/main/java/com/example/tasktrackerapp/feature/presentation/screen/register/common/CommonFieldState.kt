package com.example.tasktrackerapp.feature.presentation.screen.register.common

import com.example.tasktrackerapp.core.utils.UIText

data class CommonFieldState(
    val value: String = "",
    val isValid: Boolean = true,
    val message: UIText = UIText.DynamicString(""),
)