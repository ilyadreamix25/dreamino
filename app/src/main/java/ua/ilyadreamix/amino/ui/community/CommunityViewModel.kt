package ua.ilyadreamix.amino.ui.community

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.call.*
import io.ktor.http.*
import kotlinx.coroutines.launch
import ua.ilyadreamix.amino.data.dto.communities.CommunityInfoResponse
import ua.ilyadreamix.amino.data.dto.core.ApiState
import ua.ilyadreamix.amino.data.dto.core.BaseResponse
import ua.ilyadreamix.amino.data.dto.posts.FeedFeaturedPostsResponse
import ua.ilyadreamix.amino.data.repo.CommunitiesRepository
import ua.ilyadreamix.amino.data.repo.FeedRepository

class CommunityViewModel : ViewModel() {
    private var _communityState = mutableStateOf(ApiState<CommunityInfoResponse>())
    private var _featuredState = mutableStateOf(ApiState<FeedFeaturedPostsResponse>())

    val communityState: State<ApiState<CommunityInfoResponse>> = _communityState
    val featuredState: State<ApiState<FeedFeaturedPostsResponse>> = _featuredState

    fun getCommunityInfo(ndcId: Int) {
        viewModelScope.launch {
            _communityState.value = ApiState(isLoading = true)

            val response = CommunitiesRepository.getCommunityInfo(ndcId)

            try {
                if (response.status == HttpStatusCode.OK) {
                    val body: CommunityInfoResponse = response.body()
                    _communityState.value = ApiState(
                        data = body,
                        code = body.statusCode
                    )
                } else {
                    val body: BaseResponse = response.body()
                    _communityState.value = ApiState(
                        errorBody = body,
                        code = body.statusCode,
                        hasError = true,
                        errorMessage = body.message
                    )
                }
            } catch (e: Exception) {
                Log.e("CommunityViewModel", "getCommunityInfo: Several error", e)
                _communityState.value = ApiState(
                    code = -2,
                    hasError = true,
                    errorMessage = "Several error",
                    extras = listOf(e)
                )
            }
        }
    }

    fun getFeaturedBlogs(ndcId: Int) {
        viewModelScope.launch {
            _featuredState.value = ApiState(isLoading = true)

            val response = FeedRepository.getFeatured(ndcId)

            try {
                if (response.status == HttpStatusCode.OK) {
                    val body: FeedFeaturedPostsResponse = response.body()
                    _featuredState.value = ApiState(
                        data = body,
                        code = body.statusCode
                    )
                } else {
                    val body: BaseResponse = response.body()
                    _featuredState.value = ApiState(
                        errorBody = body,
                        code = body.statusCode,
                        hasError = true,
                        errorMessage = body.message
                    )
                }
            } catch (e: Exception) {
                Log.e("CommunityViewModel", "getFeaturedBlogs: Several error", e)
                _featuredState.value = ApiState(
                    code = -2,
                    hasError = true,
                    errorMessage = "Several error",
                    extras = listOf(e)
                )
            }
        }
    }
}