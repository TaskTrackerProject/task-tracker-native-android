package com.example.tasktrackerapp.feature.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun BasicDialog(
    title: String = "",
    message: String,
    onDismiss: () -> Unit,
    onConfirm: (() -> Unit)? = null,
    confirmButtonText: String = "OKAY",
    barrierDismissable: Boolean = true,
) {
    AlertDialog(
        onDismissRequest = {
            if (barrierDismissable) {
                onDismiss()
            }
        },
        confirmButton = {
            Button(onClick = {
                onConfirm?.invoke()
            }) {
                Text(confirmButtonText)
            }
        },
        title = {
            if (title.isNotEmpty()) {
                Text(text = title)
            }
        },
        text = {
            Text(text = message)
        }
    )
}