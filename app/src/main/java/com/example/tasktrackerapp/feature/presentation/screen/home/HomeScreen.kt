package com.example.tasktrackerapp.feature.presentation.screen.home

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.example.tasktrackerapp.feature.presentation.screen.home.navigation.HomeBottomBarGraph
import com.example.tasktrackerapp.feature.presentation.screen.home.common.HomeViewModel
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import com.example.tasktrackerapp.R
import com.example.tasktrackerapp.core.utils.UIText
import com.example.tasktrackerapp.feature.presentation.screen.home.navigation.BottomRoutes
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by remember { viewModel.state }
    val navHostController = rememberNavController()

    val onProjectClick = remember { { viewModel.onProjectClick() } }
    val onProfileClick = remember { { viewModel.onProfileClick() } }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvents.collectLatest { events ->
            when (events) {
                is HomeViewModel.UIEvents.GoToProject -> {
                    navHostController.navigate(BottomRoutes.project) {
                        popUpTo(navHostController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                }

                is HomeViewModel.UIEvents.GoToProfile -> {
                    navHostController.navigate(BottomRoutes.profile) {
                        popUpTo(navHostController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                }
            }
        }
    }

    Scaffold(
        content = { innerPadding ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                HomeBottomBarGraph(navHostController = navHostController)
            }
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    label = {
                        Text(text = UIText.StringResource(R.string.home).asString())
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Navigation Icon"
                        )
                    },
                    selected = state.currentScreen == BottomRoutes.project,
                    onClick = onProjectClick,
                )
                NavigationBarItem(
                    label = {
                        Text(text = UIText.StringResource(R.string.profile).asString())
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Navigation Icon"
                        )
                    },
                    selected = state.currentScreen == BottomRoutes.profile,
                    onClick = onProfileClick,
                )
            }
        }
    )
}