package com.example.tasktrackerapp.feature.presentation.screen.verification

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tasktrackerapp.R
import com.example.tasktrackerapp.core.utils.UIText
import com.example.tasktrackerapp.feature.domain.model.verification.VerificationParamModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@Composable
fun VerificationScreen(
    navController: NavController,
    data: VerificationParamModel,
    viewModel: VerificationViewModel = hiltViewModel()
) {
    val firstField = remember<(String) -> Unit> {
        {

        }
    }
    val secondField = remember<(String) -> Unit> {
        {

        }
    }
    val thirdField = remember<(String) -> Unit> {
        {

        }
    }
    val fourthField = remember<(String) -> Unit> {
        {

        }
    }

    Scaffold(
        modifier = Modifier.imePadding(),
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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                    ) {
                        NumberField(
                            modifier = Modifier
                                .width(50.dp)
                                .height(50.dp),
                            value = "0",
                            onValueChange = firstField,
                        )
                        NumberField(
                            modifier = Modifier
                                .width(50.dp)
                                .height(50.dp),
                            value = "0",
                            onValueChange = secondField,
                        )
                        NumberField(
                            modifier = Modifier
                                .width(50.dp)
                                .height(50.dp),
                            value = "0",
                            onValueChange = thirdField,
                        )
                        NumberField(
                            modifier = Modifier
                                .width(50.dp)
                                .height(50.dp),
                            value = "0",
                            onValueChange = fourthField,
                        )
                    }
                    Spacer(modifier = Modifier.height(30.dp))
                    Row {
                        Text(UIText.StringResource(R.string.didnt_received_code).asString())
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = UIText.StringResource(R.string.resend).asString(),
                            modifier = Modifier
                                .clickable {

                                },
                            color = MaterialTheme.colorScheme.primary,
                            textDecoration = TextDecoration.Underline,
                        )
                    }
                    Spacer(modifier = Modifier.height(100.dp))
                }
            }
        }
    )
}

@Composable
fun NumberField(
    modifier: Modifier,
    value: String = "",
    onValueChange: (data: String) -> Unit,
) {
    OutlinedTextField(
        modifier = modifier,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
        value = value,
        onValueChange = onValueChange,
        textStyle = LocalTextStyle.current.copy(
            textAlign = TextAlign.Center,
        ),
        colors = OutlinedTextFieldDefaults.colors(
            cursorColor = Color.Transparent,
            //focusedBorderColor = Color(0xFF6650a4),
            //unfocusedBorderColor = Color(0xFF625b71),
            //cursorColor = Color.Transparent,
            //errorCursorColor = Color.Transparent
        ),
    )
}

@Preview(showBackground = true)
@Composable
fun VerificationScreenPreview() {
    val navController = rememberNavController()
    val param = VerificationParamModel(id = "", email = "josefmguillen@gmail.com")
    VerificationScreen(navController, data = param)
}