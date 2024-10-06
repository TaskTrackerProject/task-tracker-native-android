package com.example.tasktrackerapp.feature.domain.entity.remote.user

data class UserEmailLoginPayloadEntity(
    val email: String,
    val password: String,
)