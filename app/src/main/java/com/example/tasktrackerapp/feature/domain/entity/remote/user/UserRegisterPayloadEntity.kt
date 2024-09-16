package com.example.tasktrackerapp.feature.domain.entity.remote.user

data class UserRegisterPayloadEntity(
    val firstName: String,
    val lastName: String,
    val email: String,
    val username: String,
    val password: String,
)