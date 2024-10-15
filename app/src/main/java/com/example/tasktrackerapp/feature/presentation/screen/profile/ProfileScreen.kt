package com.example.tasktrackerapp.feature.presentation.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tasktrackerapp.R
import com.example.tasktrackerapp.core.utils.UIText
import com.example.tasktrackerapp.feature.presentation.screen.register.RegisterScreen

@Composable
fun ProfileScreen(
    navController: NavController,
) {

    val onChangePassClick = remember {{}}
    val onLogoutClick = remember {{}}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        ItemComponent(label = "Name", value = "Josef Guillen")
        Spacer(modifier = Modifier.height(10.dp))
        ItemComponent(label = "Email", value = "josefmguillen@gmail.com")
        Spacer(modifier = Modifier.height(10.dp))
        ItemComponent(label = "Username", value = "jmguillen")
        Spacer(modifier = Modifier.weight(1f))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onChangePassClick,
        ) {
            Text(
                UIText.StringResource(R.string.change_password).asString().uppercase(),
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onLogoutClick,
        ) {
            Text(
                UIText.StringResource(R.string.logout).asString().uppercase(),
            )
        }
    }
}

@Composable
fun ItemComponent(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(text = label, fontWeight = FontWeight.Bold)
        Text(text = value)
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    val navController = rememberNavController()
    ProfileScreen(navController)
}