package ua.ilyadreamix.amino.auth.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.util.Log
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
import ua.ilyadreamix.amino.AminoApplication
import ua.ilyadreamix.amino.BuildConfig
import ua.ilyadreamix.amino.core.component.AminoLogo
import ua.ilyadreamix.amino.R
import ua.ilyadreamix.amino.auth.AuthViewModel
import ua.ilyadreamix.amino.core.component.AminoAlertDialog
import ua.ilyadreamix.amino.core.component.RoundedTextField
import ua.ilyadreamix.amino.home.HomeActivity
import ua.ilyadreamix.amino.http.dto.auth.SendPublicCertificatesRequest
import ua.ilyadreamix.amino.http.dto.auth.SignInRequest
import ua.ilyadreamix.amino.http.hash.KeyStoreUtility
import ua.ilyadreamix.amino.http.utility.OldAminoHashUtility
import ua.ilyadreamix.amino.session.SessionInfo
import ua.ilyadreamix.amino.session.SessionUtility

private val deviceId = OldAminoHashUtility.generateDeviceId()

@Composable
fun SignInScreen() {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val authViewModel = viewModel<AuthViewModel>()

    // Error alert dialog, sending certificates
    ObserveAuthViewModel(authViewModel)

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
                        authViewModel.signIn(
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

@Composable
private fun ObserveAuthViewModel(authViewModel: AuthViewModel) {
    ObserveSignInResponse(authViewModel)
    ObserveSecurityResponse(authViewModel)
}

@Composable
private fun ObserveSignInResponse(authViewModel: AuthViewModel) {

    val signInResponse = authViewModel.signInResponseState.value
    val context = LocalContext.current

    if (signInResponse.hasError) {
        AminoAlertDialog(
            onDismiss = {
                authViewModel.clearSignIn()
            },
            onConfirm = if (signInResponse.code == 270) {
                {
                    signInResponse.errorBody?.let {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.url))
                        context.startActivity(intent)
                    }
                }
            } else {
                { authViewModel.clearSignIn() }
            },
            text = {
                Text(text = signInResponse.errorMessage!!)
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

        authViewModel.sendPublicCertificates(
            SendPublicCertificatesRequest(
                certificates = KeyStoreUtility(
                    AminoApplication.getCertificateName()
                ).initKeyPair().getCertificates(),
                uid = signInResponse.data.userProfile.uid
            )
        )

        authViewModel.clearSignIn()
    }
}

@Composable
private fun ObserveSecurityResponse(authViewModel: AuthViewModel) {

    val context = LocalContext.current
    val securityResponse = authViewModel.securityResponseState.value

    if (securityResponse.hasError) {
        val message = securityResponse.errorMessage
        val code = securityResponse.code

        Log.d(
            "SignInScreen",
            "Public certificates error: $message ($code)"
        )

        authViewModel.clearSecurity()
    } else if (!securityResponse.isLoading && securityResponse.code == 0) {
        val intent = Intent(context, HomeActivity::class.java)
        context.startActivity(intent)
        (context as Activity).finish()

        authViewModel.clearSecurity()
    }
}