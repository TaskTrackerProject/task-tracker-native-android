package com.example.tasktrackerapp.feature.domain.usecase.verification

import com.example.tasktrackerapp.core.model.Either
import com.example.tasktrackerapp.core.model.FailedModel
import com.example.tasktrackerapp.core.model.SuccessModel
import com.example.tasktrackerapp.feature.domain.repository.UserRepository

class VerifyUser(private val repository: UserRepository) {
    suspend operator fun invoke(
        userId: String,
        code: String,
    ): Either<FailedModel<Any>, SuccessModel<String>> {
        return repository.verifyUser(
            userId,
            code,
        )
    }
}