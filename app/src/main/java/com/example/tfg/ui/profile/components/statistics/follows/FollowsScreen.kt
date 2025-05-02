package com.example.tfg.ui.profile.components.statistics.follows

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tfg.R
import com.example.tfg.model.user.User
import com.example.tfg.ui.common.UserPictureWithoutCache
import com.example.tfg.ui.friends.components.UserRowText
import com.example.tfg.ui.lists.listDetails.components.TopDetailsListBar
import com.example.tfg.ui.profile.components.statistics.followers.AcceptOperationDialog
import com.example.tfg.ui.theme.TFGTheme

@Composable
fun FollowsScreen(
    returnToLastScreen: () -> Unit,
    navigateToProfile: (user: User) -> Unit,
    viewModel: FollowsScreenViewModel = hiltViewModel()
) {
    TFGTheme(dynamicColor = false)
    {
        Scaffold(
            topBar = {
                TopDetailsListBar(
                    returnToLastScreen,
                    tittle = stringResource(R.string.profile_following_text)
                )
            }
        ) { innerPadding ->
            LazyColumn(
                Modifier.padding(innerPadding)
            ) {
                item {
                    HorizontalDivider()
                }
                items(viewModel.followsInfo.followsList) {
                    Row (modifier = Modifier.padding(start = 10.dp, top = 10.dp)){
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable {
                            navigateToProfile(it)
                        }.weight(1f)) {
                            val signatureKey = remember { mutableStateOf(System.currentTimeMillis().toString()) }
                            UserPictureWithoutCache(it.profilePicture, signatureKey,Modifier.size(50.dp).clip(CircleShape))
                            Column (modifier = Modifier.padding(start = 10.dp)){
                                UserRowText(it.userAlias)
                                if (it.userName != "") {
                                    UserRowText(it.userName)
                                }
                            }
                        }
                        IconButton({viewModel.changeOpenDialog()}) {
                            Icon(Icons.Default.Clear,null)
                        }
                        if (viewModel.followsInfo.deleteDialog) {
                            AcceptOperationDialog(
                                stringResource(R.string.cancel_follow),
                                { viewModel.changeOpenDialog() },
                                { viewModel.deleteFollow(it) })
                        }
                    }
                }
            }
        }
    }
}