package com.example.tasktrackerapp.feature.presentation.screen.register

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tasktrackerapp.feature.presentation.components.BasicDialog
import com.example.tasktrackerapp.feature.presentation.components.LoadingDialog
import com.example.tasktrackerapp.feature.presentation.components.rememberImeState
import com.example.tasktrackerapp.feature.presentation.screen.login.LoginEvent
import com.example.tasktrackerapp.feature.presentation.screen.login.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val state = viewModel.state

    if (state.value.showLoading) {
        LoadingDialog()
    }
    if (state.value.showBasicDialog) {
        BasicDialog(
            message = state.value.dialogMessage,
            onDismiss = {
                viewModel.onEvent(RegisterEvent.SetBasicDialogVisibility(visible = false))
            },
            onConfirm = {
                viewModel.onEvent(RegisterEvent.SetBasicDialogVisibility(visible = false))
            }
        )
    }

    Scaffold(
        modifier = Modifier.imePadding(),
        topBar = {
            TopAppBar(
                title = { Text(text = "Register") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBackIosNew,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        content = { innerPadding ->
            Surface(
                modifier = Modifier
                    .padding(innerPadding)
            ) {
                val scrollState = rememberScrollState()
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .padding(horizontal = 20.dp, vertical = 10.dp),
                    verticalArrangement = Arrangement.Center,
                ) {
                    NormalTextField(
                        value = state.value.firstNameValue,
                        onValueChange = { newVal ->
                            val text = newVal.filter { it.isLetter() || it.isWhitespace() }
                            viewModel.onEvent(RegisterEvent.FirstNameTextChanged(text))
                        },
                        label = "First Name",
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text,
                        ),
                        isValid = state.value.isFirstNameValid,
                        message = state.value.firstNameMessage,
                    )
                    NormalTextField(
                        value = state.value.lastNameValue,
                        onValueChange = { newVal ->
                            val text = newVal.filter { it.isLetter() || it.isWhitespace() }
                            viewModel.onEvent(RegisterEvent.LastNameTextChanged(text))
                        },
                        label = "Last Name",
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text,
                        ),
                        isValid = state.value.isLastNameValid,
                        message = state.value.lastNameMessage,
                    )
                    NormalTextField(
                        value = state.value.emailValue,
                        onValueChange = {
                            viewModel.onEvent(RegisterEvent.EmailTextChanged(it))
                        },
                        label = "Email",
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Email,
                        ),
                        isValid = state.value.isEmailValid,
                        message = state.value.emailMessage,
                    )
                    NormalTextField(
                        value = state.value.usernameValue,
                        onValueChange = { newVal ->
                            val maxLength = 20
                            val filteredText = newVal.filter { it.isLetterOrDigit() }
                            val text = if (filteredText.length <= maxLength) {
                                filteredText
                            } else {
                                filteredText.take(maxLength)
                            }
                            viewModel.onEvent(RegisterEvent.UserNameTextChanged(text))
                        },
                        label = "Username",
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Ascii,
                        ),
                        isValid = state.value.isUserNameValid,
                        message = state.value.usernameMessage,
                    )
                    PasswordTextField(
                        value = state.value.passwordValue,
                        isPasswordVisible = state.value.isPasswordVisible,
                        onValueChange = {
                            val maxLength = 20
                            val text = if (it.length <= maxLength) {
                                it
                            } else {
                                it.take(maxLength)
                            }
                            viewModel.onEvent(RegisterEvent.PasswordTextChanged(text))
                        },
                        onTrailIconPress = {
                            viewModel.onEvent(RegisterEvent.ChangePasswordVisibility)
                        },
                        label = "Password",
                        isValid = state.value.isPasswordValid,
                        message = state.value.passwordMessage,
                    )
                    PasswordTextField(
                        value = state.value.confirmPasswordValue,
                        isPasswordVisible = state.value.isConfirmPasswordVisible,
                        onValueChange = {
                            val maxLength = 20
                            val text = if (it.length <= maxLength) {
                                it
                            } else {
                                it.take(maxLength)
                            }
                            viewModel.onEvent(RegisterEvent.ConfirmPasswordTextChanged(text))
                        },
                        onTrailIconPress = {
                            viewModel.onEvent(RegisterEvent.ChangeConfirmPasswordVisibility)
                        },
                        label = "Confirm Password",
                        isValid = state.value.isConfirmPasswordValid,
                        message = state.value.confirmPasswordMessage,
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            viewModel.onEvent(RegisterEvent.OnRegisterPress)
                        }
                    ) {
                        Text("REGISTER")
                    }
                }
            }
        }
    )
}

@Composable
fun NormalTextField(
    value: String = "",
    onValueChange: (data: String) -> Unit,
    label: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    isValid: Boolean = true,
    message: String = "",
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        label = {
            Text(text = label)
        },
        keyboardOptions = keyboardOptions,
        isError = !isValid,
        supportingText = {
            if (!isValid) {
                Text(text = message)
            }
        }
    )
}

@Composable
fun PasswordTextField(
    value: String = "",
    isPasswordVisible: Boolean,
    onValueChange: (data: String) -> Unit,
    onTrailIconPress: () -> Unit,
    label: String,
    isValid: Boolean = true,
    message: String = "",
) {
    OutlinedTextField(
        visualTransformation =
        if (!isPasswordVisible) PasswordVisualTransformation()
        else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        trailingIcon = {
            IconButton(
                onClick = onTrailIconPress,
            ) {
                Icon(
                    imageVector =
                    if (!isPasswordVisible) Icons.Filled.VisibilityOff
                    else Icons.Filled.Visibility,
                    contentDescription = "Password",
                )
            }
        },
        label = {
            Text(text = label)
        },
        isError = !isValid,
        supportingText = {
            if (!isValid) {
                Text(text = message)
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    val navController = rememberNavController()
    RegisterScreen(navController)
}
