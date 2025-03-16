package com.example.tfg.ui.friends.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.tfg.model.user.userActivities.Activity

@Composable
fun FriendActivityItem(activity: Activity) {
    Box(
        modifier = Modifier
            .clip(AlertDialogDefaults.shape)
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.onPrimary)
    ) {
        Column(
            modifier = Modifier
                .padding(top = 10.dp)
        ) {
            Row(modifier = Modifier.padding(start = 20.dp, bottom = 10.dp)) {
                bookImageBig(activity.book.coverImage, Modifier.weight(0.5f))
                Column(
                    Modifier
                        .padding(start = 10.dp, top = 20.dp)
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    userInfoAndDate(activity)
                    Column(Modifier.weight(1f)) {

                    }
                    activityAndBookInfo(activity)
                }
            }
        }
    }
}

