package com.example.tasktrackerapp.feature.domain.usecase.login

import com.example.tasktrackerapp.feature.domain.usecase.ValidateEmptyField

data class LoginUseCase(
    val login: Login,
    val validateEmptyField: ValidateEmptyField,
)