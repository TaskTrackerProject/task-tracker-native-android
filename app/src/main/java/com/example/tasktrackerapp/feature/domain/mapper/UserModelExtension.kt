package com.example.tasktrackerapp.feature.domain.mapper

import com.example.tasktrackerapp.feature.domain.entity.remote.user.UserRegisterPayloadEntity
import com.example.tasktrackerapp.feature.domain.model.UserModel

fun UserModel.toUserRegisterPayloadEntity(): UserRegisterPayloadEntity {
    return UserRegisterPayloadEntity(
        firstName = this.firstName,
        lastName = this.lastName,
        email = this.email,
        username = this.userName,
        password = this.password,
    )
}