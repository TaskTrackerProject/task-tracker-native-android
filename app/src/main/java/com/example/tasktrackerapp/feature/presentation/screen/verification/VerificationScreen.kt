package com.example.tasktrackerapp.feature.presentation.screen.verification

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tasktrackerapp.R
import com.example.tasktrackerapp.core.navigation.Routes
import com.example.tasktrackerapp.core.utils.UIText
import com.example.tasktrackerapp.feature.domain.model.verification.VerificationParamModel
import com.example.tasktrackerapp.feature.presentation.components.LoadingDialog
import com.example.tasktrackerapp.feature.presentation.screen.register.common.RegisterViewModel
import com.example.tasktrackerapp.feature.presentation.screen.verification.common.VerificationViewModel
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@Composable
fun VerificationScreen(
    navController: NavController,
    data: VerificationParamModel,
    viewModel: VerificationViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val otpTextChange = remember<(String) -> Unit> { { viewModel.setOTPData(it) } }
    val otpFieldFocus = remember { FocusRequester() }
    val field1Click = remember {
        {
            otpFieldFocus.requestFocus()
        }
    }
    val field2Click = remember {
        {
            otpFieldFocus.requestFocus()
            viewModel.determineHighLight()
        }
    }
    val field3Click = remember {
        {
            otpFieldFocus.requestFocus()
            viewModel.determineHighLight()
        }
    }
    val field4Click = remember {
        {
            otpFieldFocus.requestFocus()
            viewModel.determineHighLight()
        }
    }
    val resendClick = remember {
        Modifier.clickable {

        }
    }
    val onSend = remember {
        {
            viewModel.verify(data.id)
        }
    }
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvents.collectLatest { events ->
            when (events) {
                is VerificationViewModel.UIEvents.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(
                        message = events.message.asString(context),
                    )
                }
                is VerificationViewModel.UIEvents.GoToHomeScreen -> {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.LOGIN) {
                            inclusive = true
                        }
                    }
                }
                is VerificationViewModel.UIEvents.ShowToast -> {
                    Toast.makeText(context, events.message.asString(context), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier.imePadding(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(text = UIText.StringResource(R.string.verify).asString())
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBackIosNew,
                            contentDescription = "Back",
                        )
                    }
                }
            )
        },
        content = { innerPadding ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                if (state.isLoading) {
                    LoadingDialog()
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp, vertical = 20.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        UIText.StringResource(R.string.code_sent_to_email, data.email).asString(),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                    )
                    Spacer(modifier = Modifier.height(50.dp))
                    Box(modifier = Modifier.fillMaxWidth()) {
                        HiddenField(
                            value = state.otpVal,
                            focusRequester = otpFieldFocus,
                            onValueChange = otpTextChange,
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                        ) {
                            NumberField(
                                isHighlight = state.field1Highlight,
                                value = state.firstFieldVal,
                                onClick = field1Click,
                            )
                            NumberField(
                                isHighlight = state.field2Highlight,
                                value = state.secondFieldVal,
                                onClick = field2Click,
                            )
                            NumberField(
                                isHighlight = state.field3Highlight,
                                value = state.thirdFieldVal,
                                onClick = field3Click,
                            )
                            NumberField(
                                isHighlight = state.field4Highlight,
                                value = state.fourthFieldVal,
                                onClick = field4Click,
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(30.dp))
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = onSend,
                    ) {
                        Text(
                            UIText.StringResource(R.string.submit).asString().uppercase(),
                        )
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    Row {
                        Text(UIText.StringResource(R.string.didnt_received_code).asString())
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = UIText.StringResource(R.string.resend).asString(),
                            modifier = Modifier.then(resendClick),
                            color = MaterialTheme.colorScheme.primary,
                            textDecoration = TextDecoration.Underline,
                        )
                    }
                    Spacer(modifier = Modifier.height(70.dp))
                }
            }
        }
    )
}

@Composable
fun HiddenField(
    focusRequester: FocusRequester,
    value: String = "",
    onValueChange: (data: String) -> Unit,
) {
    OutlinedTextField(
        modifier = Modifier
            .focusRequester(focusRequester)
            .alpha(0f)
            .width(100.dp)
            .height(50.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
        value = value,
        onValueChange = onValueChange,
        textStyle = LocalTextStyle.current.copy(
            textAlign = TextAlign.Center,
        ),
        colors = OutlinedTextFieldDefaults.colors(
            cursorColor = Color.Transparent,
        ),
    )
}

@Composable
fun NumberField(
    isHighlight: Boolean,
    value: String = "",
    onClick: () -> Unit,
) {
    val colors = OutlinedTextFieldDefaults.colors()
    val focusedColor = colors.focusedIndicatorColor
    val unfocusedColor = colors.unfocusedIndicatorColor
    val interactionSource = remember { MutableInteractionSource() }
    val clickModifier = remember {
        Modifier.clickable(
            onClick = onClick,
            interactionSource = interactionSource,
            indication = null
        )
    }
    val borderColor = if (isHighlight) focusedColor else unfocusedColor
    Box {
        OutlinedTextField(
            modifier = Modifier
                .width(50.dp)
                .height(50.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            readOnly = true,
            value = value,
            onValueChange = {

            },
            textStyle = LocalTextStyle.current.copy(
                textAlign = TextAlign.Center,
            ),
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = Color.Transparent,
                focusedBorderColor = borderColor,
                unfocusedBorderColor = borderColor,
                //errorCursorColor = Color.Transparent
            ),
        )
        Box(modifier = clickModifier.matchParentSize())
    }
}

@Preview(showBackground = true)
@Composable
fun VerificationScreenPreview() {
    val navController = rememberNavController()
    val param = VerificationParamModel(id = "", email = "josefmguillen@gmail.com")
    VerificationScreen(navController, data = param)
}