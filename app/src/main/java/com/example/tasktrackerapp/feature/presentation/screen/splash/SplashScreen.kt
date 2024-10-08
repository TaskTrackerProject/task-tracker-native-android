package com.example.tasktrackerapp.feature.presentation.screen.splash

import android.window.SplashScreenView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tasktrackerapp.core.navigation.Routes
import com.example.tasktrackerapp.feature.presentation.screen.register.RegisterScreen
import com.example.tasktrackerapp.feature.presentation.screen.splash.common.SplashViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel = hiltViewModel()
) {

    LaunchedEffect(key1 = true) {
        viewModel.uiEvents.collectLatest { events ->
            when(events) {
                is SplashViewModel.UIEvents.GoToLogin -> {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.SPLASH) {
                            inclusive = true
                        }
                    }
                }
                is SplashViewModel.UIEvents.GoToHome -> {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.SPLASH) {
                            inclusive = true
                        }
                    }
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier.imePadding(),
        content = { innerPadding ->
            Surface(
                modifier = Modifier
                    .padding(innerPadding)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            color = Color.Transparent,
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun SplashScreenView() {
    val navController = rememberNavController()
    SplashScreen(navController)
}
