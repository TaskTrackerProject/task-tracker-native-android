package com.example.tasktrackerapp.feature.domain.usecase.login

import com.example.tasktrackerapp.core.utils.UIText
import com.example.tasktrackerapp.feature.domain.model.UserModel
import com.example.tasktrackerapp.feature.domain.model.common.Either
import com.example.tasktrackerapp.feature.domain.model.common.SuccessModel
import com.example.tasktrackerapp.feature.domain.model.login.LoginResultModel
import com.example.tasktrackerapp.feature.domain.repository.UserRepository

class Login(private val repository: UserRepository) {
    suspend operator fun invoke(
        usernameOrEmail: String,
        password: String
    ): LoginResultModel {
        return repository.loginUser(usernameOrEmail, password)
    }
}