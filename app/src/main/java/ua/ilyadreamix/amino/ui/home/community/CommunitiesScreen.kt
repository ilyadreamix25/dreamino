package ua.ilyadreamix.amino.ui.home.community

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun CommunitiesScreen() {
    val communitiesViewModel = viewModel<CommunitiesViewModel>()

    if (communitiesViewModel.joinedState.value.code == -1)
        communitiesViewModel.getJoinedCommunities()
    else if (communitiesViewModel.joinedState.value.code == -2) {
        Text("Several error! ${(communitiesViewModel.joinedState.value.extras!![0] as Exception).message}")
    } else if (communitiesViewModel.joinedState.value.code == 0) {
        Text("Ok")
    } else {
        Text("Bad ${communitiesViewModel.joinedState.value.errorMessage}")
    }
}