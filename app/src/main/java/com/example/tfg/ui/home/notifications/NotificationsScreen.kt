package com.example.tfg.ui.home.notifications

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tfg.R
import com.example.tfg.ui.home.notifications.components.FriendRequestAccessRow
import com.example.tfg.ui.home.notifications.components.NotificationRowItem
import com.example.tfg.ui.lists.listDetails.components.TopDetailsListBar
import com.example.tfg.ui.theme.TFGTheme

@Composable
fun NotificationScreen(
    navigateTo: (route: String) -> Unit, returnToLastScreen: () -> Unit,
    notificationsViewModel: NotificationsViewModel = hiltViewModel()
) {
    TFGTheme {
        Scaffold(
            topBar = {
                TopDetailsListBar(
                    returnToLastScreen = returnToLastScreen,
                    tittle = stringResource(R.string.home_notifications)
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
            ) {
                FriendRequestAccessRow(notificationsViewModel, navigateTo)
                HorizontalDivider(modifier = Modifier.padding(top = 5.dp))
                NotificationRowItem(notificationsViewModel, navigateTo)
            }
        }
    }
}

