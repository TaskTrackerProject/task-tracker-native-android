package com.example.tasktrackerapp.feature.presentation.screen.home.common

import com.example.tasktrackerapp.feature.presentation.screen.home.navigation.BottomRoutes

data class HomeState(
    val message: String = "",
    val currentScreen: String = BottomRoutes.project,
)