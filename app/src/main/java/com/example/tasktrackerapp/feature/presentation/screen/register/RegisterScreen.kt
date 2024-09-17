package com.example.tasktrackerapp.feature.presentation.screen.register

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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.tasktrackerapp.core.utils.UIText
import com.example.tasktrackerapp.feature.presentation.components.BasicDialog
import com.example.tasktrackerapp.feature.presentation.components.LoadingDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current

    if (state.value.showLoading) {
        LoadingDialog()
    }

    if (state.value.showBasicDialog) {
        BasicDialog(
            message = (state.value.dialogMessage.asString(context)),
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
                title = {
                    Text(text = UIText.StringResource(R.string.register).asString(context))
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
                        onValueChange = {
                            viewModel.onEvent(RegisterEvent.FirstNameTextChanged(it))
                        },
                        label = UIText.StringResource(R.string.first_name).asString(context),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text,
                        ),
                        isValid = state.value.isFirstNameValid,
                        message = state.value.firstNameMessage.asString(context),
                    )
                    NormalTextField(
                        value = state.value.lastNameValue,
                        onValueChange = {
                            viewModel.onEvent(RegisterEvent.LastNameTextChanged(it))
                        },
                        label = UIText.StringResource(R.string.last_name).asString(context),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text,
                        ),
                        isValid = state.value.isLastNameValid,
                        message = state.value.lastNameMessage.asString(context),
                    )
                    NormalTextField(
                        value = state.value.emailValue,
                        onValueChange = {
                            viewModel.onEvent(RegisterEvent.EmailTextChanged(it))
                        },
                        label = UIText.StringResource(R.string.email).asString(context),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Email,
                        ),
                        isValid = state.value.isEmailValid,
                        message = state.value.emailMessage.asString(context),
                    )
                    NormalTextField(
                        value = state.value.usernameValue,
                        onValueChange = {
                            viewModel.onEvent(RegisterEvent.UserNameTextChanged(it))
                        },
                        label = UIText.StringResource(R.string.username).asString(context),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Ascii,
                        ),
                        isValid = state.value.isUserNameValid,
                        message = state.value.usernameMessage.asString(context),
                    )
                    PasswordTextField(
                        value = state.value.passwordValue,
                        isPasswordVisible = state.value.isPasswordVisible,
                        onValueChange = {
                            viewModel.onEvent(RegisterEvent.PasswordTextChanged(it))
                        },
                        onTrailIconPress = {
                            viewModel.onEvent(RegisterEvent.ChangePasswordVisibility)
                        },
                        label = UIText.StringResource(R.string.password).asString(context),
                        isValid = state.value.isPasswordValid,
                        message = state.value.passwordMessage.asString(context),
                    )
                    PasswordTextField(
                        value = state.value.confirmPasswordValue,
                        isPasswordVisible = state.value.isConfirmPasswordVisible,
                        onValueChange = {
                            viewModel.onEvent(RegisterEvent.ConfirmPasswordTextChanged(it))
                        },
                        onTrailIconPress = {
                            viewModel.onEvent(RegisterEvent.ChangeConfirmPasswordVisibility)
                        },
                        label = UIText.StringResource(R.string.confirm_password).asString(context),
                        isValid = state.value.isConfirmPasswordValid,
                        message = state.value.confirmPasswordMessage.asString(context),
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            viewModel.onEvent(RegisterEvent.OnRegisterPress)
                        }
                    ) {
                        Text(
                            UIText.StringResource(R.string.register).asString(context).uppercase(),
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
