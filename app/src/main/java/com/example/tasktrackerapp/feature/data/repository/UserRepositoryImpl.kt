package com.example.tasktrackerapp.feature.data.repository

import com.example.tasktrackerapp.core.model.Either
import com.example.tasktrackerapp.core.model.FailedModel
import com.example.tasktrackerapp.feature.data.datasource.remote.UserDataSource
import com.example.tasktrackerapp.feature.domain.mapper.model.toUserRegisterPayloadEntity
import com.example.tasktrackerapp.feature.domain.model.UserModel
import com.example.tasktrackerapp.core.model.SuccessModel
import com.example.tasktrackerapp.core.utils.UIText
import com.example.tasktrackerapp.feature.domain.entity.remote.user.UserVerifyPayloadEntity
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
}