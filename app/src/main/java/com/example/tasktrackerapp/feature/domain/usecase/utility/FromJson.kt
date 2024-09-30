package com.example.tasktrackerapp.feature.domain.usecase.utility

import com.example.tasktrackerapp.feature.domain.repository.UtilityRepository

class FromJson(
    private val repository: UtilityRepository
) {
    operator fun <T : Any> invoke(json: String, clazz: Class<T>) : T? {
        return repository.fromJson(json, clazz)
    }
}