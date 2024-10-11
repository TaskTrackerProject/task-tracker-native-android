package com.example.tasktrackerapp.feature.presentation.screen.home.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import com.example.tasktrackerapp.R
import com.example.tasktrackerapp.core.utils.UIText
import com.example.tasktrackerapp.feature.presentation.screen.login.common.LoginViewModel.UIEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    sealed class BottomBarScreen(
        val route: String,
        val title: UIText,
        val icon: ImageVector
    ) {
        data object Home : BottomBarScreen(
            route = "home",
            title = UIText.StringResource(R.string.home),
            icon = Icons.Default.Home
        )

        data object Profile : BottomBarScreen(
            route = "profile",
            title = UIText.StringResource(R.string.profile),
            icon = Icons.Default.Person
        )
    }

    private val _state = MutableSharedFlow<HomeState>()
    val state = _state.asSharedFlow()
}