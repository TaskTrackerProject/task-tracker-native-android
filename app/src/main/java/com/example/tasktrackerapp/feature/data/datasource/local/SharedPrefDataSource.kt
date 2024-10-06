package com.example.tasktrackerapp.feature.data.datasource.local

interface SharedPrefDataSource {
    suspend fun saveSingleData(key: String, value: String)
    suspend fun getSingleData(key: String) : String?
    suspend fun deleteSingleData(key: String)
}