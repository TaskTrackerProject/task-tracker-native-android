package com.example.tasktrackerapp.feature.presentation.screen.home.common

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tasktrackerapp.feature.presentation.screen.profile.ProfileScreen
import com.example.tasktrackerapp.feature.presentation.screen.project.ProjectScreen

@Composable
fun HomeBottomBarGraph(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = HomeViewModel.BottomBarScreen.Home.route
    ) {
        composable(route = HomeViewModel.BottomBarScreen.Home.route) {
            ProjectScreen()
        }
        composable(route = HomeViewModel.BottomBarScreen.Profile.route) {
            ProfileScreen()
        }
    }
}