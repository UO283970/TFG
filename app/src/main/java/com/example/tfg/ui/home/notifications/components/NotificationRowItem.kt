package com.example.tfg.ui.home.notifications.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.tfg.R
import com.example.tfg.ui.home.notifications.NotificationsViewModel

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun NotificationRowItem(notificationsViewModel: NotificationsViewModel, navigateTo: (String) -> Unit) {
    val listState = rememberLazyListState()

    LaunchedEffect(Unit) {
        snapshotFlow {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            val totalItems = listState.layoutInfo.totalItemsCount
            (lastVisibleItem?.index ?: 0) >= totalItems - 5
        }.collect { shouldLoadMore ->
            if (shouldLoadMore) {
                notificationsViewModel.getUserNotifications()
            }
        }
    }

    LazyColumn (state = listState){
        items(notificationsViewModel.notificationsMainState.notificationList) {
            Row(
                Modifier
                    .padding(top = 10.dp, start = 10.dp, end = 10.dp)
                    .clickable {
                        navigateTo(it.getRowOnClickRoute())
                    }, verticalAlignment = Alignment.CenterVertically) {
                GlideImage(
                    model = it.getNotificationImage(),
                    contentDescription = null,
                    failure = placeholder(R.drawable.default_user_image),
                    transition = CrossFade,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(50.dp),
                    contentScale = ContentScale.FillBounds
                )
                Text(
                    stringResource(it.getMainNotificationInfoResource(),it.getExtraInfo()),
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 5.dp),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    fontWeight = FontWeight.SemiBold
                )
                IconButton(onClick = { notificationsViewModel.deleteNotification(it) }) {
                    Icon(Icons.Filled.Clear, stringResource(id = R.string.notifications_delete_button))
                }
            }
        }
    }
}