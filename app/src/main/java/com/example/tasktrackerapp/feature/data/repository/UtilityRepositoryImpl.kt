package com.example.tasktrackerapp.feature.data.repository

import com.example.tasktrackerapp.feature.domain.repository.UtilityRepository
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import javax.inject.Inject

class UtilityRepositoryImpl @Inject constructor(private val gson: Gson) : UtilityRepository {
    override fun <T : Any> toJson(data: T): String {
        return gson.toJson(data)
    }

    override fun <T : Any> fromJson(json: String, clazz: Class<T>): T? {
        return try {
            gson.fromJson(json, clazz)
        } catch (e: JsonSyntaxException) {
            return null
        }
    }
}