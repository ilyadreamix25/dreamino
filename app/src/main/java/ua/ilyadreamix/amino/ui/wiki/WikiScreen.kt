package ua.ilyadreamix.amino.ui.wiki

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ua.ilyadreamix.amino.R
import ua.ilyadreamix.amino.ui.core.component.AminoTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WikiScreen() {
    val context = LocalContext.current

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            AminoTopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = {
                            (context as Activity).finish()
                        }
                    ) {
                        // BUG: Icon padding is too small
                        Icon(
                            imageVector = Icons.Filled.ArrowBackIos,
                            contentDescription = null,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            )
        }
    ) { insets ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(insets)
                .alpha(.75f),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Filled.Error,
                contentDescription = null,
                modifier = Modifier.size(48.dp)
            )
            Text(
                text = stringResource(id = R.string.content_type_unsupported),
                textAlign = TextAlign.Center
            )
        }
    }
}