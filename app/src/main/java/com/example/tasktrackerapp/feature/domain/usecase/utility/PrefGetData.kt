package com.example.tasktrackerapp.feature.domain.usecase.utility

import com.example.tasktrackerapp.feature.domain.repository.UtilityRepository

class PrefGetData(private val repository: UtilityRepository) {
    suspend operator fun invoke(key: String): String? {
        return repository.getPrefData(key)
    }
}