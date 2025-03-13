package com.example.tfg.ui.home.notifications.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.tfg.R
import com.example.tfg.ui.home.notifications.NotificationsViewModel

@Composable
fun notificationRowItem(notificationsViewModel: NotificationsViewModel) {
    LazyColumn {
        items(notificationsViewModel.notificationsMainState.notificationList) {
            Row(
                Modifier
                    .padding(top = 10.dp, start = 10.dp, end = 10.dp)
                    .clickable {
                        if (it.getRowOnClick() != { }) {
                            it.getRowOnClick()
                        }
                    }) {
                Image(
                    painterResource(it.getNotificationImage()),
                    stringResource(R.string.notifications_image_text),
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(50.dp),
                    contentScale = ContentScale.FillBounds
                )
                Text(
                    it.getMainNotificationInfo(),
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 5.dp),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    fontWeight = FontWeight.SemiBold
                )
                if (it.getButtonInfo() != null) {
                    Button(onClick = { it.getButtonInfo()!!.action }) {
                        Text(it.getButtonInfo()!!.tittle)
                    }
                }
                IconButton(onClick = { notificationsViewModel.deleteNotification(it) }) {
                    Icon(Icons.Filled.Clear, stringResource(id = R.string.notifications_delete_button))
                }
            }
        }
    }
}