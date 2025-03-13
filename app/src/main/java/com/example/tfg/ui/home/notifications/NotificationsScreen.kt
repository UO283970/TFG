package com.example.tfg.ui.home.notifications

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tfg.R
import com.example.tfg.ui.home.notifications.components.friendRequestAccessRow
import com.example.tfg.ui.home.notifications.components.notificationRowItem
import com.example.tfg.ui.lists.listDetails.components.topDetailsListBar
import com.example.tfg.ui.theme.TFGTheme

@Composable
fun notificationScreen(notificationsViewModel: NotificationsViewModel) {
    TFGTheme {
        Scaffold(
            topBar = {
                topDetailsListBar(
                    commonEvents = notificationsViewModel.commonEventHandler,
                    tittle = stringResource(R.string.home_notifications)
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
            ) {
                friendRequestAccessRow(notificationsViewModel)
                HorizontalDivider(modifier = Modifier.padding(top = 5.dp))
                notificationRowItem(notificationsViewModel)
            }
        }
    }
}

