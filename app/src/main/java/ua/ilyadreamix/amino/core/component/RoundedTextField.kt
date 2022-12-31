package ua.ilyadreamix.amino.core.component

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoundedTextField(
    text: String,
    modifier: Modifier = Modifier,
    startIcon: @Composable () -> Unit = {},
    label: @Composable () -> Unit = {},
    onChange: (String) -> Unit = {},
    enabled: Boolean = true
) {
    var textInput by remember { mutableStateOf(text) }

    TextField(
        value = textInput,
        onValueChange = {
            textInput = it
            onChange(it)
        },
        modifier = modifier.defaultMinSize(minHeight = 58.dp),
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.textFieldColors(
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent
        ),
        label = label,
        leadingIcon = startIcon,
        enabled = enabled,
        maxLines = 1,
        singleLine = true
    )
}