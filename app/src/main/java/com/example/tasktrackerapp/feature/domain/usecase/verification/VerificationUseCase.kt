package com.example.tasktrackerapp.feature.domain.usecase.verification

import com.example.tasktrackerapp.feature.domain.usecase.utility.PrefSaveData

data class VerificationUseCase(
    val verifyUser: VerifyUser,
    val prefSaveData: PrefSaveData,
)