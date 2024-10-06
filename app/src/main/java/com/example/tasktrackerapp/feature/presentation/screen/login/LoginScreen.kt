package com.example.tasktrackerapp.feature.presentation.screen.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tasktrackerapp.core.navigation.Routes
import com.example.tasktrackerapp.feature.presentation.screen.login.common.LoginViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val usernameChange = remember<(String) -> Unit> {
        {
            viewModel.setUserNameValue(it)
        }
    }
    val passwordChange = remember<(String) -> Unit> {
        {
            viewModel.setPasswordValue(it)
        }
    }
    val forgotPasswordClick = remember {
        Modifier.clickable {

        }
    }
    val registerClick = remember {
        Modifier.clickable {
            navController.navigate(Routes.REGISTER)
        }
    }
    val loginClick = remember {{ viewModel.onLogin() }}
    val state by remember { viewModel.state }

    Scaffold(
        content = { innerPadding ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.Center,
                ) {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.userNameValue,
                        onValueChange = usernameChange,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Username",
                            )
                        },
                        singleLine = true,
                        isError = !state.usernameIsValid,
                        supportingText = {
                            if (!state.usernameIsValid) {
                                Text(text = state.usernameMessage.asString())
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    OutlinedTextField(
                        visualTransformation =
                        if (!state.isPasswordVisible) PasswordVisualTransformation()
                        else VisualTransformation.None,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        value = state.passwordValue,
                        onValueChange = passwordChange,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Password",
                            )
                        },
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    viewModel.changePasswordVisibility()
                                }
                            ) {
                                Icon(
                                    imageVector =
                                    if (state.isPasswordVisible) Icons.Filled.VisibilityOff
                                    else Icons.Filled.Visibility,
                                    contentDescription = "Password",
                                )
                            }
                        },
                        isError = !state.passwordIsValid,
                        supportingText = {
                            if (!state.passwordIsValid) {
                                Text(text = state.passwordMessage.asString())
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Box(
                        modifier = forgotPasswordClick.fillMaxWidth(),
                        contentAlignment = Alignment.CenterEnd,
                    ) {
                        Text(
                            text = "Forgot Password",
                            textDecoration = TextDecoration.Underline,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = loginClick
                    ) {
                        Text("LOGIN")
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Box(
                        modifier = registerClick.fillMaxWidth(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = "Don't have an account?",
                            textDecoration = TextDecoration.Underline,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    }

                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    val navController = rememberNavController()
    LoginScreen(navController)
}
