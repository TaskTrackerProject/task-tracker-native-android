package com.example.tasktrackerapp.feature.domain.usecase.register

import com.example.tasktrackerapp.core.model.Either
import com.example.tasktrackerapp.core.model.SuccessModel
import com.example.tasktrackerapp.feature.domain.model.UserModel
import com.example.tasktrackerapp.feature.domain.repository.UserRepository

class RegisterUser(private val repository: UserRepository) {
    suspend operator fun invoke(user: UserModel) : Either<String, SuccessModel<String>> {
        return repository.registerUser(user)
    }
}