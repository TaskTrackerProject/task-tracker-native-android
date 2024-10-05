package com.example.tasktrackerapp.feature.domain.repository

import com.example.tasktrackerapp.core.model.Either
import com.example.tasktrackerapp.core.model.FailedModel
import com.example.tasktrackerapp.feature.domain.model.UserModel
import com.example.tasktrackerapp.core.model.SuccessModel
import com.example.tasktrackerapp.core.utils.UIText

interface UserRepository {
    suspend fun registerUser(user: UserModel): Either<UIText, SuccessModel<String>>
    suspend fun verifyUser(
        userId: String,
        optCode: String
    ): Either<FailedModel<Any>, SuccessModel<String>>
}