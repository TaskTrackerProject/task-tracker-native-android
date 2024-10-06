package com.example.tasktrackerapp.feature.domain.usecase.utility

import com.example.tasktrackerapp.feature.domain.repository.UtilityRepository

class PrefSaveData(private val repository: UtilityRepository) {
    suspend operator fun invoke(key: String, value: String) {
        return repository.savePrefData(key, value)
    }
}