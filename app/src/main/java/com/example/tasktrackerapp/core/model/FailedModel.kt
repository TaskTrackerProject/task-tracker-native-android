package com.example.tasktrackerapp.core.model

data class FailedModel<T: Any>(
    val message: String = "",
    val data: T? = null
)
