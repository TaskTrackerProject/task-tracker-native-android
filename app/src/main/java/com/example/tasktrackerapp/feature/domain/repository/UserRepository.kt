package com.example.tasktrackerapp.feature.domain.repository

import com.example.tasktrackerapp.feature.domain.model.common.Either
import com.example.tasktrackerapp.feature.domain.model.common.FailedModel
import com.example.tasktrackerapp.feature.domain.model.UserModel
import com.example.tasktrackerapp.feature.domain.model.common.SuccessModel
import com.example.tasktrackerapp.core.utils.UIText
import com.example.tasktrackerapp.feature.domain.model.login.LoginResultModel

interface UserRepository {
    suspend fun registerUser(user: UserModel): Either<UIText, SuccessModel<String>>

    suspend fun verifyUser(
        userId: String,
        optCode: String
    ): Either<FailedModel<Any>, SuccessModel<String>>

    suspend fun loginUser(
        usernameOrEmail: String,
        password: String,
    ): LoginResultModel
}