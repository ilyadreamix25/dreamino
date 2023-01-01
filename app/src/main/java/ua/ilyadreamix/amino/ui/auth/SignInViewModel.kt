package ua.ilyadreamix.amino.ui.auth

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.launch
import ua.ilyadreamix.amino.http.dto.auth.SendPublicCertificatesRequest
import ua.ilyadreamix.amino.http.dto.auth.SignInRequest
import ua.ilyadreamix.amino.http.dto.auth.SignInResponse
import ua.ilyadreamix.amino.http.dto.core.ApiState
import ua.ilyadreamix.amino.http.dto.core.BaseResponse
import ua.ilyadreamix.amino.http.module.AuthModule
import ua.ilyadreamix.amino.http.module.SecurityModule

class SignInViewModel : ViewModel() {

    private val authService = AuthModule.provideService()
    private val securityService = SecurityModule.provideService()

    private var _signInResponseState = mutableStateOf(ApiState<SignInResponse>())
    private var _securityResponseState = mutableStateOf(ApiState<BaseResponse>())

    val signInResponseState: State<ApiState<SignInResponse>> = _signInResponseState
    val securityResponseState: State<ApiState<BaseResponse>> = _securityResponseState

    fun signIn(body: SignInRequest, deviceId: String) {
        viewModelScope.launch {
            try {
                _signInResponseState.value = ApiState(isLoading = true)

                val apiResponse = authService.loginByEmail(body, deviceId)
                if (apiResponse.isSuccessful && apiResponse.body()!!.statusCode == 0) {
                    val result: SignInResponse = apiResponse.body()!!
                    _signInResponseState.value = ApiState(data = result, code = result.statusCode)
                } else {
                    val errorBody = apiResponse.errorBody()!!
                    val errorBodyReader = errorBody.charStream()

                    val gson = Gson()
                    val errorBodyModel = gson.fromJson(errorBodyReader, BaseResponse::class.java)

                    _signInResponseState.value = ApiState(
                        hasError = true,
                        errorMessage = errorBodyModel.message,
                        code = errorBodyModel.statusCode,
                        errorBody = errorBodyModel
                    )
                }
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Unknown error", e)
                _signInResponseState.value = ApiState(
                    hasError = true,
                    errorMessage = "Unknown error: $e"
                )
            }
        }
    }

    fun sendPublicCertificates(body: SendPublicCertificatesRequest) {
        viewModelScope.launch {
            try {
                _securityResponseState.value = ApiState(isLoading = true)

                val apiResponse = securityService.sendPublicKey(body)

                if (apiResponse.isSuccessful && apiResponse.body()!!.statusCode == 0) {
                    val result: BaseResponse = apiResponse.body()!!
                    _securityResponseState.value = ApiState(
                        data = result,
                        code = result.statusCode
                    )
                } else {
                    val errorBody = apiResponse.errorBody()!!
                    val errorBodyReader = errorBody.charStream()

                    val gson = Gson()
                    val errorBodyModel = gson.fromJson(errorBodyReader, BaseResponse::class.java)

                    _securityResponseState.value = ApiState(
                        hasError = true,
                        errorMessage = errorBodyModel.message,
                        code = errorBodyModel.statusCode,
                        errorBody = errorBodyModel
                    )
                }
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Unknown error", e)
                _securityResponseState.value = ApiState(
                    hasError = true,
                    errorMessage = "Unknown error: $e"
                )
            }
        }
    }

    fun clearSignIn() { _signInResponseState.value = ApiState() }
    fun clearSecurity() { _securityResponseState.value = ApiState() }
}