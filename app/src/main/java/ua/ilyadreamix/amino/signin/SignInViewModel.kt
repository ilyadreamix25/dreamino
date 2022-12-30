package ua.ilyadreamix.amino.signin

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ua.ilyadreamix.amino.http.dto.auth.SignInRequest
import ua.ilyadreamix.amino.http.dto.auth.SignInResponse
import ua.ilyadreamix.amino.http.dto.core.ApiState
import ua.ilyadreamix.amino.http.dto.core.BaseResponse
import ua.ilyadreamix.amino.http.module.AuthModule
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor() : ViewModel() {
    private val service = AuthModule.provideAuthService()

    private var _signInResponseState = mutableStateOf(ApiState<SignInResponse>())
    val signInResponseState: State<ApiState<SignInResponse>> = _signInResponseState

    fun signIn(body: SignInRequest, deviceId: String) {
        viewModelScope.launch {
            try {
                _signInResponseState.value = ApiState(isLoading = true)

                val apiResponse = service.loginByEmail(body, deviceId)
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
                Log.e("SignInViewModel", "Unknown error", e)
                _signInResponseState.value = ApiState(
                    hasError = true,
                    errorMessage = "Unknown error: $e"
                )
            }
        }
    }

    fun clear() { _signInResponseState.value = ApiState() }
}