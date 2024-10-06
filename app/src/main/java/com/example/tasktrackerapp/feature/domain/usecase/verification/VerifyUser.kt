package com.example.tasktrackerapp.feature.domain.usecase.verification

import com.example.tasktrackerapp.feature.domain.model.common.Either
import com.example.tasktrackerapp.feature.domain.model.common.FailedModel
import com.example.tasktrackerapp.feature.domain.model.common.SuccessModel
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