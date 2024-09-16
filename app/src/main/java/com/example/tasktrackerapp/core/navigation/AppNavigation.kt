package com.example.tasktrackerapp.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tasktrackerapp.feature.presentation.screen.home.HomeScreen
import com.example.tasktrackerapp.feature.presentation.screen.login.LoginScreen
import com.example.tasktrackerapp.feature.presentation.screen.register.RegisterScreen
import com.example.tasktrackerapp.feature.presentation.screen.verification.VerificationScreen

@Composable
fun AppNavigationGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.LOGIN) {
        composable(route = Routes.LOGIN) {
            LoginScreen(navController = navController)
        }
        composable(route = Routes.REGISTER) {
            RegisterScreen(navController = navController)
        }
        composable(route = Routes.VERIFICATION) {
            VerificationScreen(navController = navController)
        }
        composable(route = Routes.HOME) {
            HomeScreen(navController = navController)
        }
    }
}