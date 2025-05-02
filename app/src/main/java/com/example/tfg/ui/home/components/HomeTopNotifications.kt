package com.example.tfg.ui.home.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tfg.R
import com.example.tfg.ui.common.navHost.HomeRoutesItems

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNotifications(navigateTo: (String) -> Unit, hasNotifications: Boolean) {
    TopAppBar(
        windowInsets = WindowInsets(0.dp),
        title = { Text(text = stringResource(R.string.home_top_bar_title)) },
        expandedHeight = 50.dp,
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
        actions = {
            IconButton(onClick = {navigateTo(HomeRoutesItems.NotificationScreen.route) }) {
                if(hasNotifications){
                    Icon(painterResource(R.drawable.with_notifications_icon), stringResource(R.string.notifications), modifier = Modifier.size(24.dp))
                }else{
                    Icon(painterResource(R.drawable.notification_icon), stringResource(R.string.notifications), modifier = Modifier.size(24.dp))
                }
            }
        }
    )
}
