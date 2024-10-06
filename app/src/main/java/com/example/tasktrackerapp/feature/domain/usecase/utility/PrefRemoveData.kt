package com.example.tasktrackerapp.feature.domain.usecase.utility

import com.example.tasktrackerapp.feature.domain.repository.UtilityRepository

class PrefRemoveData(private val repository: UtilityRepository) {
    suspend operator fun invoke(key: String) {
        return repository.removePrefData(key)
    }
}