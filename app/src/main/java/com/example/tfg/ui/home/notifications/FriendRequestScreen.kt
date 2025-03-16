package com.example.tfg.ui.home.notifications

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tfg.R
import com.example.tfg.model.user.User
import com.example.tfg.ui.lists.listDetails.components.TopDetailsListBar
import com.example.tfg.ui.theme.TFGTheme

@Composable
fun FriendsRequestScreen(
    returnToLastScreen: () -> Unit,
    navigateToProfile: (user: User) -> Unit,
    notificationsViewModel: NotificationsViewModel = hiltViewModel()
) {
    TFGTheme {
        Scaffold(
            topBar = {
                TopDetailsListBar(
                    returnToLastScreen = returnToLastScreen,
                    tittle = stringResource(R.string.home_notifications_friend_requests)
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
            ) {
                LazyColumn(modifier = Modifier.padding(start = 10.dp, end = 10.dp)) {
                    items(notificationsViewModel.notificationsMainState.friendRequests) {
                        Row(
                            Modifier
                                .padding(top = 10.dp)
                        ) {
                            Row(
                                Modifier
                                    .weight(1f)
                                    .clickable {
                                        navigateToProfile(it)
                                    }, verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painterResource(it.profilePicture),
                                    stringResource(R.string.notifications_image_text),
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .size(50.dp),
                                    contentScale = ContentScale.FillBounds
                                )
                                Text(
                                    it.userAlias,
                                    modifier = Modifier
                                        .padding(start = 5.dp),
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 1,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            Button(onClick = { notificationsViewModel.acceptFriendRequest(it) }) {
                                Text(stringResource(R.string.accept_button))
                            }
                            IconButton(onClick = { notificationsViewModel.deleteFriendRequest(it) }) {
                                Icon(Icons.Filled.Clear, stringResource(id = R.string.notifications_delete_button))
                            }
                        }
                    }
                }
            }
        }
    }
}