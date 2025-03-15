package com.example.tfg.ui.friends

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tfg.model.user.User
import com.example.tfg.ui.friends.components.friendActivityItem
import com.example.tfg.ui.friends.components.searchBarFriendsScreen
import com.example.tfg.ui.theme.TFGTheme


@Composable
fun friendsScreen(navigateToProfile: (user: User,route: String) -> Unit, viewModel: FriendsViewModel = hiltViewModel()) {
    val state by viewModel.friendsInfo.collectAsState()

    TFGTheme(dynamicColor = false)
    {
        Scaffold { innerPadding ->
            Column(Modifier.padding(innerPadding)) {
                searchBarFriendsScreen(viewModel,state,navigateToProfile)
                Box() {
                    LazyColumn(
                        Modifier
                            .padding(top = 10.dp, start = 10.dp, end = 10.dp),
                        verticalArrangement = Arrangement.spacedBy((10).dp)
                    ) {
                        items(state.followedActivity){
                            friendActivityItem(it)
                        }
                    }
                }
            }
        }
    }
}