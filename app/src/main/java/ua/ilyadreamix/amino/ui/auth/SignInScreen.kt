package ua.ilyadreamix.amino.ui.auth

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.animation.Crossfade
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
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import ua.ilyadreamix.amino.AminoApplication
import ua.ilyadreamix.amino.BuildConfig
import ua.ilyadreamix.amino.ui.core.component.AminoLogo
import ua.ilyadreamix.amino.R
import ua.ilyadreamix.amino.data.dto.auth.LoginByEmailRequest
import ua.ilyadreamix.amino.data.dto.security.SendPublicKeyRequest
import ua.ilyadreamix.amino.ui.core.component.AminoAlertDialog
import ua.ilyadreamix.amino.ui.core.component.AminoRoundedTextField
import ua.ilyadreamix.amino.ui.home.HomeActivity
import ua.ilyadreamix.amino.utility.keystore.KeyStoreUtility
import ua.ilyadreamix.amino.data.utility.OldAminoHashUtility
import ua.ilyadreamix.amino.utility.session.SessionInfo
import ua.ilyadreamix.amino.utility.session.SessionUtility

private val deviceId = OldAminoHashUtility.generateDeviceId()

@Composable
fun SignInScreen() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val signInViewModel = viewModel<SignInViewModel>()

    // Error alert dialog, sending certificates
    ObserveAuthViewModel(signInViewModel)

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
                AminoRoundedTextField(
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
                AminoRoundedTextField(
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

                val isButtonDisabled =
                    signInViewModel.loginState.value.isLoading ||
                    signInViewModel.publicKeyState.value.isLoading

                Button(
                    onClick = {
                        signInViewModel.loginByEmail(
                            body = LoginByEmailRequest(
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
                    enabled = !isButtonDisabled
                ) {
                    Crossfade(
                        targetState = isButtonDisabled,
                        modifier = Modifier
                            .height(58.dp)
                            .fillMaxWidth()
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            if (it) CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center)
                            )
                            else Text(
                                text = stringResource(id = R.string.login),
                                modifier = Modifier.align(Alignment.Center),
                                fontSize = 16.sp
                            )
                        }
                    }
                }
                Text(
                    text = stringResource(
                        id = R.string.dreamino_by,
                        BuildConfig.VERSION_NAME
                    ),
                    modifier = Modifier.alpha(.5f),
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    lineHeight = 18.sp
                )
            }
        }
    }
}

@Composable
private fun ObserveAuthViewModel(signInViewModel: SignInViewModel) {
    val signInResponse = signInViewModel.loginState.value
    val securityResponse = signInViewModel.publicKeyState.value

    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

    if (signInResponse.hasError || securityResponse.hasError) {
        AminoAlertDialog(
            onDismiss = {
                signInViewModel.clearLogin()
                signInViewModel.clearPublicKey()
            },
            onConfirm = if (signInResponse.code == 270) ({
                signInResponse.errorBody?.let {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.url))
                    context.startActivity(intent)
                }
            }) else ({
                signInResponse.extras?.let {
                    if (it.isNotEmpty() && signInResponse.code == -2) {
                        (it[0] as Exception).message?.let { errorMsg ->
                            clipboardManager.setText(
                                AnnotatedString(errorMsg)
                            )
                        }
                    }
                }

                signInViewModel.clearLogin()
                signInViewModel.clearPublicKey()
            }),
            text = {
                Text(
                    text = signInResponse.errorMessage ?: securityResponse.errorMessage!!
                )
            },
            okButtonText = {
                Text(text = stringResource(id = R.string.ok))
            },
            dismissButtonText = {
                Text(text = stringResource(id = R.string.cancel))
            }
        )
    } else if (!signInResponse.isLoading && signInResponse.code == 0) {
        SessionUtility.saveSession(
            SessionInfo(
                lastLogin = System.currentTimeMillis(),
                secret = signInResponse.data!!.secret,
                sessionId = signInResponse.data.sid,
                deviceId = deviceId,
                userId = signInResponse.data.userProfile.uid
            )
        )

        signInViewModel.sendPublicCertificates(
            SendPublicKeyRequest(
                keyChain = KeyStoreUtility(
                    AminoApplication.getCertificateName()
                ).initKeyPair().getChain(),
                uid = signInResponse.data.userProfile.uid
            )
        )

        signInViewModel.clearLogin()
    }

    if (securityResponse.hasError) {
        val message = securityResponse.errorMessage
        val code = securityResponse.code

        Log.e(
            "SignInScreen",
            "Public certificates error: $message ($code) ($securityResponse)"
        )

        SessionUtility.deleteSession()
    } else if (!securityResponse.isLoading && securityResponse.code == 0) {
        val intent = Intent(context, HomeActivity::class.java)
        context.startActivity(intent)
        (context as Activity).finish()

        signInViewModel.clearPublicKey()
    }
}