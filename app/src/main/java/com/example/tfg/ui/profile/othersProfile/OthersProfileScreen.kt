package com.example.tfg.ui.profile.othersProfile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tfg.R
import com.example.tfg.ui.common.DescText
import com.example.tfg.ui.common.LoadingProgress
import com.example.tfg.ui.lists.listDetails.components.TopDetailsListBar
import com.example.tfg.ui.profile.components.MainUserProfileInfo
import com.example.tfg.ui.profile.components.ProfileLists
import com.example.tfg.ui.theme.TFGTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OthersProfileScreen(
    navigateTo: (route: String) -> Unit,
    returnToLastScreen: () -> Unit,
    navigateToRouteWithId: (String, String) -> Unit,
    viewModel: OthersProfileViewModel = hiltViewModel()
) {

    val state by viewModel.profileInfo.collectAsState()

    key(state.refreshUserState) {
        if (state.userInfoLoaded) {
            TFGTheme(dynamicColor = false)
            {
                PullToRefreshBox(
                    isRefreshing = state.isRefreshing,
                    onRefresh = { viewModel.refreshUser() },
                ) {
                    Scaffold(
                        topBar = {
                            TopDetailsListBar(
                                returnToLastScreen,
                                tittle = state.user.userAlias.trim()
                            )
                        }) { innerPadding ->
                        Column(
                            Modifier
                                .padding(innerPadding)
                                .verticalScroll(rememberScrollState())
                        ) {
                            Column(Modifier.padding(start = 10.dp, end = 5.dp)) {
                                MainUserProfileInfo(state.user, navigateToRouteWithId, viewModel.mainUserState.getMainUser())
                                if (state.user.description.trim() != "") {
                                    DescText(3, state.user.description.trim())
                                }
                                ProfileButton(state.user.followState, navigateTo, { viewModel.changeToNotFollowing() }, { viewModel.followUser() })
                                if(!state.user.defaultList.isEmpty()){
                                    ProfileLists(state.user.defaultList, state.user.userList, navigateTo) { viewModel.listDetails(it) }
                                }else{
                                    Column(verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
                                        Icon(painterResource(R.drawable.private_icon),null, modifier = Modifier.fillMaxSize(0.5f))
                                        Text(stringResource(R.string.profile_private_text))
                                    }
                                }
                            }

                        }
                    }
                }
            }
        } else {
            LoadingProgress()
        }
    }
}