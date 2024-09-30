package com.example.tasktrackerapp.core.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class JsonConverter<T>(private val gson: Gson = Gson()) {

    // Serialize a data class to JSON string
    fun toJson(data: T): String {
        return gson.toJson(data)
    }

    // Deserialize a JSON string back to a data class
    fun fromJson(json: String, clazz: Class<T>): T {
        return gson.fromJson(json, clazz)
    }

    // Alternative: Deserialize JSON with a generic type (useful for complex types)
    private inline fun <reified T> fromJsonWithType(json: String): T {
        val type = object : TypeToken<T>() {}.type
        return gson.fromJson(json, type)
    }
}