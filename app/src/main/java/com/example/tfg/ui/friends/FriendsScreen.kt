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
import com.example.tfg.ui.friends.components.friendActivityItem
import com.example.tfg.ui.friends.components.searchBarFriendsScreen
import com.example.tfg.ui.theme.TFGTheme

@Composable
fun friendsScreen(viewModel: FriendsViewModel) {
    val state by viewModel.friendsInfo.collectAsState()

    TFGTheme(dynamicColor = false)
    {
        Scaffold { innerPadding ->
            Column(Modifier.padding(innerPadding)) {
                searchBarFriendsScreen(viewModel,state)
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