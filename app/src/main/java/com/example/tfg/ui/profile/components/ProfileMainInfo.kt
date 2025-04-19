package com.example.tfg.ui.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.example.tfg.model.user.User
import com.example.tfg.ui.common.UserPictureWithoutCache

@Composable
fun MainUserProfileInfo(user: User?, navigateToRouteWithId: (String, String) -> Unit) {
    Row {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            ProfileImage(user?.profilePicture!!)
            UserNameAndDate(user, navigateToRouteWithId)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProfileImage(profilePicture: String) {
    val signatureKey = remember { mutableStateOf(System.currentTimeMillis().toString()) }

    UserPictureWithoutCache(profilePicture, signatureKey, Modifier.size(100.dp).clip(CircleShape))
}

@Composable
fun UserNameAndDate(user: User?,  navigateToRouteWithId: (String, String) -> Unit) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        if(user?.userName != ""){
            Text(
                user?.userName ?: "",
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Statistics(user?.followers ?: 0, user?.following ?: 0, user?.numReviews ?: 0,navigateToRouteWithId, user?.userId ?: "")
    }
}

