package com.example.tasktrackerapp.feature.domain.repository

import com.example.tasktrackerapp.core.model.Either
import com.example.tasktrackerapp.feature.domain.model.UserModel
import com.example.tasktrackerapp.core.model.SuccessModel

interface UserRepository {
    suspend fun registerUser(user: UserModel) : Either<String, SuccessModel<String>>
}