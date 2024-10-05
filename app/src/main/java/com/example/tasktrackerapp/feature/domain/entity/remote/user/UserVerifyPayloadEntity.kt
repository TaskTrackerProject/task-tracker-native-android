package com.example.tasktrackerapp.feature.domain.entity.remote.user

data class UserVerifyPayloadEntity(
    val id: String,
    val code: String,
)