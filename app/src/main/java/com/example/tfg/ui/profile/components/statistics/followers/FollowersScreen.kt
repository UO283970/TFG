package com.example.tfg.ui.profile.components.statistics.followers

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tfg.R
import com.example.tfg.model.user.User
import com.example.tfg.model.user.userFollowStates.UserFollowStateEnum
import com.example.tfg.ui.friends.components.ProfileUserImage
import com.example.tfg.ui.friends.components.UserRowText
import com.example.tfg.ui.lists.listDetails.components.TopDetailsListBar
import com.example.tfg.ui.profile.othersProfile.ProfileButton
import com.example.tfg.ui.theme.TFGTheme

@Composable
fun FollowersScreen(
    returnToLastScreen: () -> Unit,
    navigateTo: (String) -> Unit,
    navigateToProfile: (user: User) -> Unit,
    viewModel: FollowersScreenViewModel = hiltViewModel()
) {
    if(viewModel.followersInfo.infoLoaded) {
        TFGTheme(dynamicColor = false)
        {
            Scaffold(
                topBar = {
                    TopDetailsListBar(
                        returnToLastScreen,
                        tittle = stringResource(R.string.profile_followers_text)
                    )
                }
            ) { innerPadding ->
                LazyColumn(
                    Modifier.padding(innerPadding)
                ) {
                    items(viewModel.followersInfo.followersList) {
                        HorizontalDivider()
                        Row(modifier = Modifier.padding(start = 10.dp, top = 10.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable {
                                navigateToProfile(it)
                            }.weight(1f)) {
                                ProfileUserImage(it.profilePicture)
                                Column(modifier = Modifier.padding(start = 10.dp)) {
                                    UserRowText(it.userAlias)
                                    if (it.userName != "") {
                                        UserRowText(it.userName)
                                    }
                                }
                            }
                            val user = it
                            if(user.followState != UserFollowStateEnum.OWN){
                                ProfileButton(it.followState, navigateTo,{viewModel.changeToNotFollowing(user)}, {viewModel.followUser(user)})
                            }
                            IconButton({ viewModel.deleteFollower(it) }) {
                                Icon(Icons.Default.Clear, stringResource(R.string.notifications_delete_button))
                            }
                        }
                    }
                }
            }
        }
    }
}