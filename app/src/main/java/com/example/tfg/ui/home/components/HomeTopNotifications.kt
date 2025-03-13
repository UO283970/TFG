package com.example.tfg.ui.home.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tfg.R
import com.example.tfg.ui.home.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun topNotifications(viewModel: HomeViewModel) {
    TopAppBar(
        windowInsets = TopAppBarDefaults.windowInsets,
        title = { Text(text = "") },
        expandedHeight = 50.dp,
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
        actions = {
            IconButton(onClick = { viewModel.navigateToNotifications() }) {
                Icon(Icons.Outlined.Notifications, stringResource(R.string.notifications))
            }
        }
    )
}
