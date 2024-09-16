package com.example.tasktrackerapp.feature.domain.entity.remote

data class CommonResponseEntity<T : Any?>(
    val message: String?,
    val data: T? = null,
)