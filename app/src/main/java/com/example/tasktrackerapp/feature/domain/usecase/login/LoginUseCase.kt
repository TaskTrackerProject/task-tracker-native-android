package com.example.tasktrackerapp.feature.domain.usecase.login

import com.example.tasktrackerapp.feature.domain.usecase.ValidateEmptyField
import com.example.tasktrackerapp.feature.domain.usecase.utility.PrefSaveData
import com.example.tasktrackerapp.feature.domain.usecase.utility.ToJson

data class LoginUseCase(
    val login: Login,
    val validateEmptyField: ValidateEmptyField,
    val toJson: ToJson,
    val prefSaveData: PrefSaveData,
)