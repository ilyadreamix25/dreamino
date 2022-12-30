package ua.ilyadreamix.amino.core.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun AminoAlertDialog(
    onDismiss: () -> Unit = {},
    onConfirm: () -> Unit = {},
    okButtonText: @Composable () -> Unit = {},
    dismissButtonText: @Composable () -> Unit = {},
    text: @Composable (() -> Unit)? = null,
    icon: @Composable (() -> Unit)? = null
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = onConfirm
            ) { okButtonText() }
        },
        dismissButton = {
            TextButton(
                onClick = onConfirm
            ) { dismissButtonText() }
        },
        text = text,
        icon = icon
    )
}