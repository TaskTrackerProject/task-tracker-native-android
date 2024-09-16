package com.example.tasktrackerapp.core.utils

object Utility {
    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun capitalizedInitialLetter(data: String): String {
        val capitalizedText = data.split(" ")
            .joinToString(" ") { word -> word.replaceFirstChar { it.uppercase() } }
        return capitalizedText
    }
}