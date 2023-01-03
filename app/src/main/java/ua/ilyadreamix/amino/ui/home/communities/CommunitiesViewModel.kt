package ua.ilyadreamix.amino.ui.home.communities

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.call.*
import io.ktor.http.*
import kotlinx.coroutines.launch
import ua.ilyadreamix.amino.data.dto.communities.JoinedCommunitiesResponse
import ua.ilyadreamix.amino.data.dto.core.ApiState
import ua.ilyadreamix.amino.data.dto.core.BaseResponse
import ua.ilyadreamix.amino.data.repo.CommunitiesRepository

class CommunitiesViewModel : ViewModel() {
    private var _joinedState = mutableStateOf(ApiState<JoinedCommunitiesResponse>())
    val joinedState: State<ApiState<JoinedCommunitiesResponse>> = _joinedState

    fun getJoinedCommunities() {
        viewModelScope.launch {
            try {
                _joinedState.value = ApiState(isLoading = true)

                val response = CommunitiesRepository.getJoinedCommunities()

                if (response.status == HttpStatusCode.OK) {
                    val body: JoinedCommunitiesResponse = response.body()
                    _joinedState.value = ApiState(
                        data = body,
                        code = body.statusCode
                    )
                } else {
                    val body: BaseResponse = response.body()
                    _joinedState.value = ApiState(
                        errorBody = body,
                        code = body.statusCode,
                        hasError = true,
                        errorMessage = body.message
                    )
                }
            } catch (e: Exception) {
                _joinedState.value = ApiState(
                    code = -2,
                    hasError = true,
                    errorMessage = "Several error",
                    extras = listOf(e)
                )
            }
        }
    }
}