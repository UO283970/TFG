package com.example.tfg.ui.profile.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tfg.R
import com.example.tfg.ui.profile.ProfileScreenEvent
import com.example.tfg.ui.profile.ProfileViewModel

@Composable
fun statistics(viewModel: ProfileViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        statisticTexts(stringResource(R.string.profile_rating_text)) { viewModel.onEvent(ProfileScreenEvent.RatingButtonClick) }
        statisticTexts(stringResource(R.string.profile_review_text)) { viewModel.onEvent(ProfileScreenEvent.ReviewsButtonClick) }
        statisticTexts(stringResource(R.string.profile_following_text)) { viewModel.onEvent(ProfileScreenEvent.FollowedButtonClick) }
        statisticTexts(stringResource(R.string.profile_followers_text)) { viewModel.onEvent(ProfileScreenEvent.FollowersButtonClick) }
    }
}

@Composable
private fun statisticTexts(mainText: String, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick })
    {
        Text(1.toString(), fontWeight = FontWeight.SemiBold)
        Text(
            mainText,
            fontWeight = FontWeight.SemiBold, fontSize = 14.sp
        )
    }
}