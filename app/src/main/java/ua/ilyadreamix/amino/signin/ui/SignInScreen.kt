package ua.ilyadreamix.amino.signin.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import ua.ilyadreamix.amino.BuildConfig
import ua.ilyadreamix.amino.core.component.AminoLogo
import ua.ilyadreamix.amino.R
import ua.ilyadreamix.amino.core.component.AminoAlertDialog
import ua.ilyadreamix.amino.core.component.RoundedTextField
import ua.ilyadreamix.amino.http.dto.auth.SignInRequest
import ua.ilyadreamix.amino.http.utility.OldAminoHashUtility
import ua.ilyadreamix.amino.signin.SignInViewModel

@Composable
fun SignInScreen() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val signInViewModel = viewModel<SignInViewModel>()
    val response = signInViewModel.signInResponseState.value

    if (response.hasError) {
        val context = LocalContext.current
        AminoAlertDialog(
            onDismiss = {
                signInViewModel.clear()
            },
            onConfirm = if (response.code == 270) {
                {
                    response.errorBody?.let {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.url))
                        context.startActivity(intent)
                    }
                }
            } else {
                {
                    signInViewModel.clear()
                }
            },
            text = {
                Text(text = response.errorMessage!!)
            },
            okButtonText = {
                Text(text = stringResource(id = R.string.ok))
            },
            dismissButtonText = {
                Text(text = stringResource(id = R.string.cancel))
            }
        )
    } else if (!response.isLoading && response.code == 0) {
        // TODO: Save session and go to the HomeActivity
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AminoLogo(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                )
                Text(
                    text = stringResource(id = R.string.login_to_the_account),
                    fontSize = 22.sp
                )

                // Email
                RoundedTextField(
                    text = email,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp, bottom = 16.dp),
                    label = {
                        Text(
                            text = stringResource(id = R.string.email)
                        )
                    },
                    startIcon = {
                        Icon(
                            imageVector = Icons.Filled.Email,
                            contentDescription = null
                        )
                    },
                    onChange = { email = it }
                )
                // Password
                RoundedTextField(
                    text = password,
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text(
                            text = stringResource(id = R.string.password)
                        )
                    },
                    startIcon = {
                        Icon(
                            imageVector = Icons.Filled.Password,
                            contentDescription = null
                        )
                    },
                    onChange = { password = it }
                )
            }

            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .systemBarsPadding(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        val deviceId = OldAminoHashUtility.generateDeviceId()
                        signInViewModel.signIn(
                            body = SignInRequest(
                                email = email,
                                secret = "0 $password",
                                deviceId = deviceId
                            ),
                            deviceId = deviceId
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .height(58.dp),
                    shape = RoundedCornerShape(16.dp),
                ) {
                    Text(
                        text = stringResource(id = R.string.login)
                    )
                }
                Text(
                    text = stringResource(id = R.string.dreamino_by, BuildConfig.VERSION_NAME),
                    modifier = Modifier.alpha(.5f),
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    lineHeight = 18.sp
                )
            }
        }
    }
}