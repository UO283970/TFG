package com.example.tfg.ui.profile.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.tfg.R
import com.example.tfg.ui.common.CommonEventHandler
import com.example.tfg.ui.friends.components.friendActivityItem
import com.example.tfg.ui.lists.listDetails.components.topDetailsListBar
import com.example.tfg.ui.profile.ProfileViewModel
import com.example.tfg.ui.theme.TFGTheme

@Composable
fun onlyReviews(commonEventHandler: CommonEventHandler, viewModel: ProfileViewModel) {
    TFGTheme(dynamicColor = false)
    {
        Scaffold(
            topBar = {
                topDetailsListBar(
                    commonEventHandler,
                    tittle = stringResource(R.string.profile_rating_text)
                )
            }
        ) { innerPadding ->
            LazyColumn(
                Modifier
                    .padding(innerPadding)
            ) {
                items(viewModel.profileInfo.profileReviews) {
                    friendActivityItem(it)
                }
            }
        }
    }
}

