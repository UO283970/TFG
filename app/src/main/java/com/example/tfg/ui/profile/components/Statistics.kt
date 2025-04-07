package com.example.tfg.ui.profile.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tfg.R
import com.example.tfg.model.user.User
import com.example.tfg.ui.common.navHost.ProfileNavigationItems

@Composable
fun Statistics(user: User?, navigateTo: (route: String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        StatisticTexts(stringResource(R.string.profile_review_text), user?.numReviews ?: 0) {
            navigateTo(ProfileNavigationItems.UserReviews.route)
        }
        Spacer(modifier = Modifier.weight(1.0f))
        StatisticTexts(stringResource(R.string.profile_followers_text), user?.followers ?: 0) {
            navigateTo(ProfileNavigationItems.UserFollowers.route)
        }

        Spacer(modifier = Modifier.weight(1.0f))
        StatisticTexts(stringResource(R.string.profile_following_text), user?.following ?: 0){
            navigateTo(ProfileNavigationItems.UserFollows.route)
        }
        Spacer(modifier = Modifier.weight(1.0f))
    }
}

@Composable
private fun StatisticTexts(mainText: String, total: Int, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick) )
    {
        Text(total.toString(), fontWeight = FontWeight.SemiBold)
        Text(
            mainText,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}