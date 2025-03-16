package com.example.tfg.ui.home.notifications.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tfg.R
import com.example.tfg.ui.common.navHost.HomeRoutesItems
import com.example.tfg.ui.home.notifications.NotificationsViewModel

@Composable
fun FriendRequestAccessRow(viewModel: NotificationsViewModel, navigateTo: (String) -> Unit) {
    Row(modifier = Modifier
        .clickable {
            viewModel.obtainFriendRequests()
            navigateTo(HomeRoutesItems.FriendRequestsScreen.route)
        }
        .padding(start = 10.dp, end = 10.dp)
        .fillMaxWidth()) {
        Box(modifier = Modifier.border(2.dp, MaterialTheme.colorScheme.onBackground, CircleShape)) {
            Icon(
                painterResource(R.drawable.friend_requests),
                stringResource(R.string.home_notifications_friend_requests_icon),
                modifier = Modifier
                    .size(50.dp)
                    .padding(5.dp)
            )
        }
        Column(Modifier.padding(start = 5.dp)) {
            Text(
                stringResource(R.string.notifications_friend_requests),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                fontSize = 22.sp
            )
            Text(
                stringResource(R.string.notifications_friend_small_text),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f)
            )
        }
    }
}