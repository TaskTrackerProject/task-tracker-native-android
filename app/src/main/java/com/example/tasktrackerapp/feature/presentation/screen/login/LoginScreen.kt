package com.example.tasktrackerapp.feature.presentation.screen.login

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tasktrackerapp.R
import com.example.tasktrackerapp.core.navigation.Routes
import com.example.tasktrackerapp.core.utils.UIText
import com.example.tasktrackerapp.feature.presentation.components.LoadingDialog
import com.example.tasktrackerapp.feature.presentation.screen.login.common.LoginViewModel
import com.example.tasktrackerapp.feature.presentation.screen.register.common.RegisterViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current
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
    val loginClick = remember { { viewModel.onLogin() } }
    val snackbarHostState = remember { SnackbarHostState() }
    val state by remember { viewModel.state }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvents.collectLatest { events ->
            when (events) {
                is LoginViewModel.UIEvents.GoToHome -> {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.LOGIN) {
                            inclusive = true
                        }
                    }
                }

                is LoginViewModel.UIEvents.GoToVerification -> {
                    navController.navigate("${Routes.VERIFICATION}/${events.data}")
                }

                is LoginViewModel.UIEvents.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(
                        message = events.message.asString(context),
                    )
                }

                is LoginViewModel.UIEvents.ShowToast -> {
                    Toast.makeText(context, events.message.asString(context), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = { innerPadding ->
            if (state.isLoading) {
                LoadingDialog()
            }
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
                        label = {
                            Text(
                                text = UIText.StringResource(R.string.username_or_email).asString()
                            )
                        },
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
                        label = {
                            Text(
                                text = UIText.StringResource(R.string.password).asString()
                            )
                        },
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
                            text = UIText.StringResource(R.string.forgot_password).asString(),
                            textDecoration = TextDecoration.Underline,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = loginClick
                    ) {
                        Text(UIText.StringResource(R.string.login).asString().uppercase())
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Box(
                        modifier = registerClick.fillMaxWidth(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = UIText.StringResource(R.string.dont_have_account).asString(),
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
