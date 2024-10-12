package com.example.tasktrackerapp.feature.presentation.screen.home.common

data class HomeState(
    val message: String = "",
    val isProjectSelected: Boolean = true,
    val isProfileSelected: Boolean = false,
)