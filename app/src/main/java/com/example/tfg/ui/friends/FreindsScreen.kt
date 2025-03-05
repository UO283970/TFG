package com.example.tfg.ui.friends

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tfg.ui.friends.components.friendActivityItem
import com.example.tfg.ui.friends.components.searchBarFriendsScreen
import com.example.tfg.ui.theme.TFGTheme

@Composable
fun friendsScreen() {
    TFGTheme(dynamicColor = false)
    {
        Scaffold() { innerPadding ->
            Column(Modifier.padding(innerPadding)) {
                searchBarFriendsScreen()
                Box() {
                    Column(
                        Modifier
                            .verticalScroll(rememberScrollState())
                            .padding(top = 10.dp, start = 10.dp, end = 10.dp),
                        verticalArrangement = Arrangement.spacedBy((10).dp)
                    ) {
                        friendActivityItem()
                    }
                }
            }
        }
    }
}

@Preview(name = "LightMode", showBackground = true, showSystemUi = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "Dark Mode", showSystemUi = true)
@Composable
fun previewCompsFriends() {
    friendsScreen()
}