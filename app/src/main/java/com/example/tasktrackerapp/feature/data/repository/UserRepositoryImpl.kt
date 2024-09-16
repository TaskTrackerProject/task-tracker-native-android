package com.example.tasktrackerapp.feature.data.repository

import com.example.tasktrackerapp.core.model.Either
import com.example.tasktrackerapp.feature.data.datasource.remote.UserDataSource
import com.example.tasktrackerapp.feature.domain.mapper.toUserRegisterPayloadEntity
import com.example.tasktrackerapp.feature.domain.model.UserModel
import com.example.tasktrackerapp.core.model.SuccessModel
import com.example.tasktrackerapp.feature.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource
) : UserRepository {
    override suspend fun registerUser(user: UserModel): Either<String, SuccessModel<String>> {
        try {
            val payload = user.toUserRegisterPayloadEntity()
            val result = userDataSource.registerUser(payload)
            return if (result.code() == 201 && result.body() != null) {
                val userId = result.body()?.data?.id ?: ""
                Either.Right(
                    value = SuccessModel(
                        message = result.body()!!.message ?: "Registration success",
                        data = userId,
                    ),
                )
            } else {
                Either.Left(value = result.body()?.message ?: "Registration failed")
            }
        } catch (e: Exception) {
            return Either.Left(value = e.message ?: "Registration failed")
        }
    }
}