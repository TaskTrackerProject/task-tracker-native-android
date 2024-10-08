package com.example.tasktrackerapp.feature.domain.usecase.register

import com.example.tasktrackerapp.feature.domain.usecase.ValidateEmptyField
import com.example.tasktrackerapp.feature.domain.usecase.utility.ToJson

data class RegisterUseCase(
    val registerUser: RegisterUser,
    val validateEmptyField: ValidateEmptyField,
    val validateEmail: RegisterValidateEmail,
    val validatePassword: RegisterValidatePassword,
    val validateConfirmPassword: RegisterValidateConfirmPassword,
    val toJson: ToJson,
)