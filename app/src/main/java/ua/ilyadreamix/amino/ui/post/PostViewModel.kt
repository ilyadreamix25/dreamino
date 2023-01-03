package ua.ilyadreamix.amino.ui.post

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.call.*
import io.ktor.http.*
import kotlinx.coroutines.launch
import ua.ilyadreamix.amino.data.dto.core.ApiState
import ua.ilyadreamix.amino.data.dto.core.BaseResponse
import ua.ilyadreamix.amino.data.dto.posts.BlogInfoResponse
import ua.ilyadreamix.amino.data.repo.BlogRepository

class PostViewModel : ViewModel() {
    private var _blogState = mutableStateOf(ApiState<BlogInfoResponse>())
    val blogState: State<ApiState<BlogInfoResponse>> = _blogState

    fun getBlogInfo(ndcId: Int, blogId: String) {
        viewModelScope.launch {
            _blogState.value = ApiState(isLoading = true)

            val response = BlogRepository.getBlogInfo(ndcId, blogId)

            try {
                if (response.status == HttpStatusCode.OK) {
                    val body: BlogInfoResponse = response.body()
                    _blogState.value = ApiState(
                        data = body,
                        code = body.statusCode
                    )

                    Log.d("TS", body.timestamp)
                } else {
                    val body: BaseResponse = response.body()
                    _blogState.value = ApiState(
                        errorBody = body,
                        code = body.statusCode,
                        hasError = true,
                        errorMessage = body.message
                    )
                }
            } catch (e: Exception) {
                Log.e("PostViewModel", "getBlogInfo: Several error", e)
                _blogState.value = ApiState(
                    code = -2,
                    hasError = true,
                    errorMessage = "Several error",
                    extras = listOf(e)
                )
            }
        }
    }
}