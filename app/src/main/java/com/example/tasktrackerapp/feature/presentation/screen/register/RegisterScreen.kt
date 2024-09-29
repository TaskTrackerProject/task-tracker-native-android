package com.example.tasktrackerapp.feature.presentation.screen.register

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tasktrackerapp.R
import com.example.tasktrackerapp.core.constants.AppConstants
import com.example.tasktrackerapp.core.navigation.Routes
import com.example.tasktrackerapp.core.utils.UIText
import com.example.tasktrackerapp.feature.presentation.components.BasicDialog
import com.example.tasktrackerapp.feature.presentation.components.LoadingDialog
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val firstNameState by remember { viewModel.firstNameState }
    val lastNameState by remember { viewModel.lastNameState }
    val emailState by remember { viewModel.emailState }
    val usernameState by remember { viewModel.usernameState }
    val passwordState by remember { viewModel.passwordState }
    val confirmPasswordState by remember { viewModel.confirmPasswordState }
    val showLoading by remember { viewModel.showLoading }
    val dialogState by remember { viewModel.dialogState }
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val snackbarHostState = remember { SnackbarHostState() }

    val firstNameChanged = remember<(String) -> Unit> {
        {
            viewModel.setFirstNameValue(it)
        }
    }
    val lastNameChanged = remember<(String) -> Unit> {
        {
            viewModel.setLastNameValue(it)
        }
    }
    val emailChanged = remember<(String) -> Unit> {
        {
            viewModel.setEmailValue(it)
        }
    }
    val usernameChanged = remember<(String) -> Unit> {
        {
            viewModel.setUserNameValue(it)
        }
    }
    val passwordChanged = remember<(String) -> Unit> {
        {
            viewModel.setPasswordValue(it)
        }
    }
    val passwordVisibilityChanged = remember {
        {
            viewModel.changePasswordVisibility()
        }
    }
    val confirmPasswordChanged = remember<(String) -> Unit> {
        {
            viewModel.setConfirmPasswordValue(it)
        }
    }
    val confirmPasswordVisibilityChanged = remember {
        {
            viewModel.changeConfirmPasswordVisibility()
        }
    }
    val onRegisterClick = remember {
        {
            viewModel.onRegister()
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvents.collectLatest { events ->
            when(events) {
                is RegisterViewModel.UIEvents.GoToVerificationPage -> {
                    //navController.navigate
                    navController.popBackStack()
                    navController.navigate(Routes.VERIFICATION)
                }
                is RegisterViewModel.UIEvents.ShowFailedSnackBar -> {
                    snackbarHostState.showSnackbar(
                        message = events.message.asString(context),
                    )
                }
                is RegisterViewModel.UIEvents.ShowSuccessSnackBar -> {
                    snackbarHostState.showSnackbar(
                        message = events.message.asString(context),
                    )
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier.imePadding(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = UIText.StringResource(R.string.register).asString())
                },
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
            if (showLoading) {
                LoadingDialog()
            }
            if (dialogState.showDialog) {
                BasicDialog(
                    message = (dialogState.dialogMessage.asString()),
                    onDismiss = {
                        viewModel.setBasicDialogState(isVisible = false)
                    },
                    onConfirm = {
                        viewModel.setBasicDialogState(isVisible = false)
                    }
                )
            }
            Surface(
                modifier = Modifier
                    .padding(innerPadding)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .padding(horizontal = 20.dp, vertical = 10.dp),
                    verticalArrangement = Arrangement.Center,
                ) {
                    NormalTextField(
                        value = firstNameState.value,
                        onValueChange = firstNameChanged,
                        label = UIText.StringResource(R.string.first_name).asString(),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text,
                        ),
                        isValid = firstNameState.isValid,
                        message = firstNameState.message.asString(),
                    )
                    NormalTextField(
                        value = lastNameState.value,
                        onValueChange = lastNameChanged,
                        label = UIText.StringResource(R.string.last_name).asString(),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text,
                        ),
                        isValid = lastNameState.isValid,
                        message = lastNameState.message.asString(),
                    )
                    NormalTextField(
                        value = emailState.value,
                        onValueChange = emailChanged,
                        label = UIText.StringResource(R.string.email).asString(),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Email,
                        ),
                        isValid = emailState.isValid,
                        message = emailState.message.asString(),
                    )
                    NormalTextField(
                        value = usernameState.value,
                        onValueChange = usernameChanged,
                        label = UIText.StringResource(R.string.username).asString(),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Ascii,
                        ),
                        isValid = usernameState.isValid,
                        message = usernameState.message.asString(),
                    )
                    PasswordTextField(
                        value = passwordState.value,
                        isPasswordVisible = passwordState.isValueVisible,
                        onValueChange = passwordChanged,
                        onTrailIconPress = passwordVisibilityChanged,
                        label = UIText.StringResource(R.string.password).asString(),
                        isValid = passwordState.isValid,
                        message = passwordState.message.asString(),
                    )
                    PasswordTextField(
                        value = confirmPasswordState.value,
                        isPasswordVisible = confirmPasswordState.isValueVisible,
                        onValueChange = confirmPasswordChanged,
                        onTrailIconPress = confirmPasswordVisibilityChanged,
                        label = UIText.StringResource(R.string.confirm_password).asString(),
                        isValid = confirmPasswordState.isValid,
                        message = confirmPasswordState.message.asString(),
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = onRegisterClick,
                    ) {
                        Text(
                            UIText.StringResource(R.string.register).asString().uppercase(),
                        )
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
