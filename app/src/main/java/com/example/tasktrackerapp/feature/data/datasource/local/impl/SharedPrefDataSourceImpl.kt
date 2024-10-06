package com.example.tasktrackerapp.feature.data.datasource.local.impl

import android.content.SharedPreferences
import com.example.tasktrackerapp.feature.data.datasource.local.SharedPrefDataSource

class SharedPrefDataSourceImpl(private val sharedPref: SharedPreferences) : SharedPrefDataSource {
    override suspend fun saveSingleData(key: String, value: String) {
        sharedPref.edit().putString(key, value).apply()
    }

    override suspend fun getSingleData(key: String): String? {
        return sharedPref.getString(key, "")
    }

    override suspend fun deleteSingleData(key: String) {
        sharedPref.edit().remove(key).apply()
    }
}