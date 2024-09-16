package com.example.tasktrackerapp.core.model

data class SuccessModel<T: Any>(
    val message: String = "",
    val data: T? = null,
)
