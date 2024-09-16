package com.example.tasktrackerapp.core.model

data class ResultModel<T: Any>(
    val isSuccess: Boolean,
    val message: String = "",
    val data: T? = null,
)