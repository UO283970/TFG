package com.example.tfg.ui.profile.components.statistics.followers

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tfg.R
import com.example.tfg.model.user.User
import com.example.tfg.model.user.userFollowStates.UserFollowStateEnum
import com.example.tfg.ui.common.UserPictureWithoutCache
import com.example.tfg.ui.friends.components.UserRowText
import com.example.tfg.ui.lists.listDetails.components.TopDetailsListBar
import com.example.tfg.ui.profile.othersProfile.ProfileButton
import com.example.tfg.ui.theme.TFGTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FollowersScreen(
    returnToLastScreen: () -> Unit,
    navigateTo: (String) -> Unit,
    navigateToProfile: (user: User) -> Unit,
    viewModel: FollowersScreenViewModel = hiltViewModel()
) {
    if (viewModel.followersInfo.infoLoaded) {
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
                    item {
                        HorizontalDivider()
                    }
                    items(viewModel.followersInfo.followersList) {
                        Row(modifier = Modifier.padding(start = 10.dp, top = 10.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                                .clickable {
                                    navigateToProfile(it)
                                }
                                .weight(1f)) {
                                val signatureKey = remember { mutableStateOf(System.currentTimeMillis().toString()) }
                                UserPictureWithoutCache(it.profilePicture, signatureKey, Modifier
                                    .size(50.dp)
                                    .clip(CircleShape))
                                Column(modifier = Modifier.padding(start = 10.dp)) {
                                    UserRowText(it.userAlias)
                                    if (it.userName != "") {
                                        UserRowText(it.userName)
                                    }
                                }
                            }
                            val user = it
                            if (user.followState != UserFollowStateEnum.OWN) {
                                ProfileButton(it.followState, navigateTo, { viewModel.changeToNotFollowing(user) }, { viewModel.followUser(user) })
                            }
                            if(viewModel.mainUserState.getMainUser()?.userId == viewModel.userId){
                                IconButton({ viewModel.changeOpenDialog() }) {
                                    Icon(Icons.Default.Clear, null)
                                }
                            }
                            if (viewModel.followersInfo.deleteDialog) {
                                AcceptOperationDialog(
                                    stringResource(R.string.delete_from_friends),
                                    { viewModel.changeOpenDialog() },
                                    { viewModel.deleteFollower(it) })
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AcceptOperationDialog(title: String, close: () -> Unit, accept: () -> Unit) {
    Dialog(
        onDismissRequest = {
            close()
        }
    ){
        Card {
            Column (modifier = Modifier.padding(top = 15.dp, start = 10.dp, end = 10.dp, bottom = 5.dp)){
                Text(title, textAlign = TextAlign.Center)
                Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End){
                    TextButton({close()}) {
                        Text(stringResource(R.string.dismiss_operation_dialog))
                    }
                    TextButton({
                        accept()
                        close()
                    }) {
                        Text(stringResource(R.string.accept_operation_dialog))
                    }
                }
            }
        }
    }

}