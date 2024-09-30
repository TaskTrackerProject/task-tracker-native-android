package com.example.tasktrackerapp.feature.domain.repository

interface UtilityRepository {
    fun <T : Any> toJson(data: T): String
    fun <T : Any> fromJson(json: String, clazz: Class<T>): T?
}