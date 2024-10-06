package com.example.tasktrackerapp.feature.domain.usecase.register

import com.example.tasktrackerapp.feature.domain.model.common.Either
import com.example.tasktrackerapp.feature.domain.model.common.SuccessModel
import com.example.tasktrackerapp.core.utils.UIText
import com.example.tasktrackerapp.feature.domain.model.UserModel
import com.example.tasktrackerapp.feature.domain.repository.UserRepository

class RegisterUser(private val repository: UserRepository) {
    suspend operator fun invoke(user: UserModel) : Either<UIText, SuccessModel<String>> {
        return repository.registerUser(user)
    }
}