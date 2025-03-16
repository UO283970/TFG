package com.example.tfg.ui.profile.components.statistics.reviews

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tfg.R
import com.example.tfg.ui.friends.components.FriendActivityItem
import com.example.tfg.ui.lists.listDetails.components.TopDetailsListBar
import com.example.tfg.ui.theme.TFGTheme

@Composable
fun OnlyReviews(
    returnToLastScreen: () -> Unit, viewModel: ReviewsScreenViewModel = hiltViewModel()
) {
    TFGTheme(dynamicColor = false)
    {
        Scaffold(
            topBar = {
                TopDetailsListBar(
                    returnToLastScreen,
                    tittle = stringResource(R.string.profile_rating_text)
                )
            }
        ) { innerPadding ->
            LazyColumn(
                Modifier
                    .padding(innerPadding)
            ) {
                items(viewModel.profileReviewsInfo.profileReviews) {
                    FriendActivityItem(it)
                }
            }
        }
    }
}

