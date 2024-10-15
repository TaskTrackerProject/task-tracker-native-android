package com.example.tasktrackerapp.feature.presentation.screen.home

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tasktrackerapp.feature.presentation.screen.home.common.HomeViewModel
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.tasktrackerapp.R
import com.example.tasktrackerapp.core.utils.UIText
import com.example.tasktrackerapp.feature.presentation.screen.profile.ProfileScreen
import com.example.tasktrackerapp.feature.presentation.screen.project.ProjectScreen
import com.example.tasktrackerapp.feature.presentation.screen.register.RegisterScreen
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state by remember { viewModel.state }
    //val navHostController = rememberNavController()
    val pagerState = rememberPagerState(pageCount = { 2 })

    val onProjectClick = remember { { viewModel.onProjectClick() } }
    val onProfileClick = remember { { viewModel.onProfileClick() } }

    val homeLabel = remember { UIText.StringResource(R.string.home).asString(context) }
    val profileLabel = remember { UIText.StringResource(R.string.profile).asString(context) }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvents.collectLatest { events ->
            when (events) {
                is HomeViewModel.UIEvents.GoToProject -> {
//                    navHostController.navigate(BottomRoutes.project) {
//                        popUpTo(navHostController.graph.findStartDestination().id)
//                        launchSingleTop = true
//                    }
                    pagerState.scrollToPage(0)
                }

                is HomeViewModel.UIEvents.GoToProfile -> {
//                    navHostController.navigate(BottomRoutes.profile) {
//                        popUpTo(navHostController.graph.findStartDestination().id)
//                        launchSingleTop = true
//                    }
                    pagerState.scrollToPage(1)
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (state.isProjectSelected) UIText.StringResource(R.string.home)
                            .asString() else UIText.StringResource(R.string.profile).asString(),
                    )
                },
                actions = {
                    Button(
                        modifier = Modifier,
                        onClick = {

                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Icon",
                            modifier = Modifier.width(20.dp).height(30.dp)
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            UIText.StringResource(R.string.create).asString().uppercase(),
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                }
            )
        },
        content = { innerPadding ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                //HomeBottomBarGraph(navHostController = navHostController)
                HorizontalPager(
                    state = pagerState,
                    userScrollEnabled = false
                ) { page ->
                    when (page) {
                        0 -> ProjectScreen(navController)
                        1 -> ProfileScreen(navController)
                    }
                }
            }
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    label = {
                        Text(text = homeLabel)
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Navigation Icon"
                        )
                    },
                    selected = state.isProjectSelected,
                    onClick = onProjectClick,
                )
                NavigationBarItem(
                    label = {
                        Text(text = profileLabel)
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Navigation Icon"
                        )
                    },
                    selected = state.isProfileSelected,
                    onClick = onProfileClick,
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
    HomeScreen(navController)
}