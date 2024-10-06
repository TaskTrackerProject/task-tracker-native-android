package com.example.tasktrackerapp.feature.data.repository

import com.example.tasktrackerapp.feature.domain.model.common.Either
import com.example.tasktrackerapp.feature.domain.model.common.FailedModel
import com.example.tasktrackerapp.feature.data.datasource.remote.UserDataSource
import com.example.tasktrackerapp.feature.domain.mapper.model.toUserRegisterPayloadEntity
import com.example.tasktrackerapp.feature.domain.model.UserModel
import com.example.tasktrackerapp.feature.domain.model.common.SuccessModel
import com.example.tasktrackerapp.core.utils.UIText
import com.example.tasktrackerapp.core.utils.Utility
import com.example.tasktrackerapp.feature.domain.entity.remote.user.UserEmailLoginPayloadEntity
import com.example.tasktrackerapp.feature.domain.entity.remote.user.UserUsernameLoginPayloadEntity
import com.example.tasktrackerapp.feature.domain.entity.remote.user.UserVerifyPayloadEntity
import com.example.tasktrackerapp.feature.domain.model.login.LoginResultModel
import com.example.tasktrackerapp.feature.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource
) : UserRepository {
    override suspend fun registerUser(user: UserModel): Either<UIText, SuccessModel<String>> {
        val payload = user.toUserRegisterPayloadEntity()
        val result = userDataSource.registerUser(payload)
        return if (result.isSuccess) {
            Either.Right(SuccessModel(message = result.message, data = result.data))
        }
        else {
            Either.Left(result.message)
        }
    }

    override suspend fun verifyUser(
        userId: String,
        optCode: String
    ): Either<FailedModel<Any>, SuccessModel<String>> {
        val payload = UserVerifyPayloadEntity(id = userId, code = optCode)
        val result = userDataSource.verifyUser(payload)
        return if (result.isSuccess) {
            Either.Right(SuccessModel(message = result.message, data = result.data ?: ""))
        } else {
            Either.Left(FailedModel(message = result.message))
        }
    }

    override suspend fun loginUser(
        usernameOrEmail: String,
        password: String
    ): LoginResultModel {
        if (Utility.isValidEmail(usernameOrEmail)) {
            val payload = UserEmailLoginPayloadEntity(
                email = usernameOrEmail,
                password = password,
            )
            val result = userDataSource.loginViaEmail(payload)
            return result
        } else {
            val payload = UserUsernameLoginPayloadEntity(
                username = usernameOrEmail,
                password = password
            )
            val result = userDataSource.loginViaUsername(payload)
            return result
        }
    }
}