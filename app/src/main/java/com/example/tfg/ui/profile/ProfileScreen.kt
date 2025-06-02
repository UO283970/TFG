package com.example.tfg.ui.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tfg.R
import com.example.tfg.ui.common.DescText
import com.example.tfg.ui.common.LoadingProgress
import com.example.tfg.ui.common.navHost.ProfileNavigationItems
import com.example.tfg.ui.profile.components.EditButton
import com.example.tfg.ui.profile.components.MainUserProfileInfo
import com.example.tfg.ui.profile.components.ProfileLists
import com.example.tfg.ui.theme.TFGTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navigateTo: (route: String) -> Unit,
    navigateToRouteWithId: (String, String) -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state by viewModel.profileInfo.collectAsState()

    if (state.infoLoaded) {
        TFGTheme(dynamicColor = false)
        {
            PullToRefreshBox(
                isRefreshing = state.isRefreshing,
                onRefresh = { viewModel.refreshProfile() },
            ) {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    viewModel.mainUserState.getMainUser()?.userAlias?.trim() ?: "",
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 1,
                                    fontSize = 22.sp
                                )
                            },
                            actions = {
                                IconButton(onClick = { navigateTo(ProfileNavigationItems.ProfileConfiguration.route) }) {
                                    Icon(Icons.Outlined.Settings, stringResource(R.string.settings_button))
                                }
                            },
                            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
                        )
                    }) { innerPadding ->

                    Column(
                        Modifier
                            .padding(innerPadding)
                            .verticalScroll(rememberScrollState())
                    ) {

                        Column(Modifier.padding(start = 10.dp, end = 5.dp)) {
                            MainUserProfileInfo(viewModel.mainUserState.getMainUser(), navigateToRouteWithId, viewModel.mainUserState.getMainUser())
                            if (viewModel.mainUserState.getMainUser()?.description?.trim() != "") {
                                DescText(3, viewModel.mainUserState.getMainUser()?.description?.trim() ?: "")
                            }
                            EditButton(navigateTo, viewModel)
                            ProfileLists(
                                viewModel.listsState.getDefaultLists(), viewModel.listsState.getOwnLists(), navigateTo
                            ) { viewModel.listDetails(it) }
                        }
                    }
                }
            }
        }
    } else {
        LoadingProgress()
    }
}