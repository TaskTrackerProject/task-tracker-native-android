package com.example.tasktrackerapp.feature.presentation.screen.home.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tasktrackerapp.feature.presentation.screen.home.common.HomeViewModel
import com.example.tasktrackerapp.feature.presentation.screen.profile.ProfileScreen
import com.example.tasktrackerapp.feature.presentation.screen.project.ProjectScreen

@Composable
fun HomeBottomBarGraph(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = BottomRoutes.project
    ) {
        composable(route = BottomRoutes.project) {
            ProjectScreen()
        }
        composable(route = BottomRoutes.profile) {
            ProfileScreen()
        }
    }
}