package ua.ilyadreamix.amino.ui.auth

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.call.*
import io.ktor.http.*
import kotlinx.coroutines.launch
import ua.ilyadreamix.amino.data.dto.auth.LoginByEmailRequest
import ua.ilyadreamix.amino.data.dto.auth.LoginByEmailResponse
import ua.ilyadreamix.amino.data.dto.core.ApiState
import ua.ilyadreamix.amino.data.dto.core.BaseResponse
import ua.ilyadreamix.amino.data.dto.security.SendPublicKeyRequest
import ua.ilyadreamix.amino.data.repo.AuthRepository
import ua.ilyadreamix.amino.data.repo.SecurityRepository

class SignInViewModel : ViewModel() {
    private var _loginState = mutableStateOf(ApiState<LoginByEmailResponse>())
    private var _publicKeyState = mutableStateOf(ApiState<BaseResponse>())

    val loginState: State<ApiState<LoginByEmailResponse>> = _loginState
    val publicKeyState: State<ApiState<BaseResponse>> = _publicKeyState

    fun loginByEmail(body: LoginByEmailRequest, deviceId: String) {
        viewModelScope.launch {
            try {
                _loginState.value = ApiState(isLoading = true)

                val response = AuthRepository.loginByEmail(body, deviceId)

                if (response.status == HttpStatusCode.OK) {
                    val responseBody: LoginByEmailResponse = response.body()
                    _loginState.value = ApiState(
                        data = responseBody,
                        code = responseBody.statusCode
                    )
                } else {
                    val responseBody: BaseResponse = response.body()
                    _loginState.value = ApiState(
                        errorBody = responseBody,
                        code = responseBody.statusCode,
                        hasError = true,
                        errorMessage = responseBody.message
                    )
                }
            } catch (e: Exception) {
                Log.e("SignInViewModel", "loginByEmail: Several error", e)
                _loginState.value = ApiState(
                    code = -2,
                    hasError = true,
                    errorMessage = "Several error",
                    extras = listOf(e)
                )
            }
        }
    }

    fun sendPublicCertificates(body: SendPublicKeyRequest) {
        viewModelScope.launch {
            try {
                _publicKeyState.value = ApiState(isLoading = true)

                val apiResponse = SecurityRepository.sendPublicKey(body)
                val responseBody: BaseResponse = apiResponse.body()

                _publicKeyState.value = ApiState(
                    data = if (responseBody.statusCode == 0) responseBody else null,
                    code = responseBody.statusCode,
                    hasError = responseBody.statusCode != 0,
                    errorMessage =
                        if (responseBody.statusCode != 0) responseBody.message
                        else null,
                    errorBody =
                        if (responseBody.statusCode != 0) responseBody
                        else null,
                )
            } catch (e: Exception) {
                Log.e("SignInViewModel", "sendPublicCertificates: Several error", e)
                _publicKeyState.value = ApiState(
                    code = -2,
                    hasError = true,
                    errorMessage = "Several error",
                    extras = listOf(e)
                )
            }
        }
    }

    fun clearLogin() { _loginState.value = ApiState() }
    fun clearPublicKey() { _publicKeyState.value = ApiState() }
}