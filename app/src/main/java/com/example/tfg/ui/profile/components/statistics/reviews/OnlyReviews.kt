package com.example.tfg.ui.profile.components.statistics.reviews

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tfg.R
import com.example.tfg.model.user.User
import com.example.tfg.ui.common.FriendsItem
import com.example.tfg.ui.lists.listDetails.components.TopDetailsListBar
import com.example.tfg.ui.theme.TFGTheme

@Composable
fun OnlyReviews(
    returnToLastScreen: () -> Unit,
    navigateToProfile: (user: User) -> Unit,
    navigateTo: (route: String) -> Unit,
    navigateToSearch: (author: String, searchFor: String) -> Unit,
    viewModel: ReviewsScreenViewModel = hiltViewModel()
) {
    TFGTheme(dynamicColor = false)
    {
        Scaffold(
            topBar = {
                TopDetailsListBar(
                    returnToLastScreen,
                    tittle = stringResource(R.string.profile_review_text)
                )
            }
        ) { innerPadding ->
            Column(
                Modifier
                    .padding(innerPadding)
            ) {
                HorizontalDivider()
                LazyColumn {
                    items(viewModel.profileReviewsInfo.profileReviews) {
                        FriendsItem(it, navigateToProfile, { viewModel.setBookDetails(it) }, { navigateTo(it) }, navigateToSearch)
                    }
                }
            }

        }
    }
}

