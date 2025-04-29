package com.example.tfg.ui.friends

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tfg.R
import com.example.tfg.model.user.User
import com.example.tfg.ui.common.FriendsItem
import com.example.tfg.ui.friends.components.SearchBarFriendsScreen
import com.example.tfg.ui.theme.TFGTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendsScreen(
    navigateToProfile: (user: User) -> Unit,
    navigateTo: (route: String) -> Unit,
    navigateToSearch: (author: String, searchFor: String) -> Unit,
    viewModel: FriendsViewModel = hiltViewModel()
) {
    val state by viewModel.friendsInfo.collectAsState()
    val listState = rememberLazyListState()

    LaunchedEffect(Unit) {
        snapshotFlow {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            val totalItems = listState.layoutInfo.totalItemsCount
            (lastVisibleItem?.index ?: 0) >= totalItems - 5
        }.collect { shouldLoadMore ->
            if (shouldLoadMore) {
                viewModel.getFollowedActivity()
            }
        }
    }

    if (state.activityLoaded) {
        TFGTheme(dynamicColor = false)
        {
            Scaffold { innerPadding ->

                Column(Modifier.padding(innerPadding)) {
                    SearchBarFriendsScreen(viewModel, state, navigateToProfile)
                    PullToRefreshBox(
                        isRefreshing = state.isRefreshing,
                        onRefresh = { viewModel.refreshActivities() },
                    ) {
                        LazyColumn(
                            Modifier
                                .padding(top = 10.dp, start = 10.dp, end = 10.dp)
                                .fillMaxHeight(),
                            verticalArrangement = Arrangement.spacedBy((10).dp),
                            state = listState
                        ) {
                            if(state.followedActivity.isEmpty()){
                                item{
                                    Column(
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.fillParentMaxSize()
                                    ) {
                                        Text(stringResource(R.string.friends_start_message), textAlign = TextAlign.Center)
                                    }
                                }
                            }else {
                                items(state.followedActivity) {
                                    FriendsItem(it,{ navigateToProfile(it) },{viewModel.setBookForDetails(it)},{navigateTo(it)}, navigateToSearch)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}