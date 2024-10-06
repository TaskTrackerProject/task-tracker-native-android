package com.example.tasktrackerapp.feature.domain.model.login

import com.example.tasktrackerapp.core.utils.UIText

data class LoginResultModel(
    val isSuccess: Boolean,
    val message: UIText = UIText.DynamicString(""),
    val isNotVerified: Boolean = false,
    val token: String = "",
    val email: String = "",
    val id: String = "",
)