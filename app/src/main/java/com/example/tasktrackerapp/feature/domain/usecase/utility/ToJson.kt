package com.example.tasktrackerapp.feature.domain.usecase.utility

import com.example.tasktrackerapp.feature.domain.repository.UtilityRepository

class ToJson(
    private val repository: UtilityRepository
) {
    operator fun <T : Any> invoke(data: T) : String {
        return repository.toJson(data)
    }
}