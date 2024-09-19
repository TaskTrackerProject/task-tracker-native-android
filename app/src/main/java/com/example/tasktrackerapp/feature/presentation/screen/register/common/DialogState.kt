package com.example.tasktrackerapp.feature.presentation.screen.register.common

import com.example.tasktrackerapp.core.utils.UIText

data class DialogState(
    val showDialog: Boolean = false,
    val dialogMessage: UIText = UIText.DynamicString(""),
)