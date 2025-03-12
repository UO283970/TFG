package com.example.tfg.ui.profile.components.editScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tfg.R
import com.example.tfg.ui.lists.listDetails.components.topDetailsListBar
import com.example.tfg.ui.profile.ProfileScreenEvent
import com.example.tfg.ui.profile.ProfileViewModel
import com.example.tfg.ui.theme.TFGTheme

@Composable
fun EditScreen(profileViewModel: ProfileViewModel) {
    TFGTheme(dynamicColor = false)
    {
        Scaffold(
            topBar = {
                topDetailsListBar(
                    profileViewModel.profileInfo.commonEventHandler,
                    stringResource(R.string.edit_profile)
                )
            }
        ) { innerPadding ->
            Column(
                Modifier
                    .padding(innerPadding)
            ) {
                HorizontalDivider()
                Column(
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            painterResource(profileViewModel.profileEditState.userProfilePicture),
                            contentDescription = "",
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(100.dp)
                                .fillMaxWidth()
                        )
                        Text(
                            stringResource(R.string.edit_change_profile_image),
                            modifier = Modifier.clickable { /*TODO: Se cambia la foto de perfil*/ })
                    }
                    Column(modifier = Modifier.padding(start = 5.dp)) {
                        editProfileUserNameTextField(profileViewModel)
                        editProfileUserAliasTextField(profileViewModel)
                        editProfileUserDescriptionTextField(profileViewModel, Modifier.weight(1f))
                        profileEditSwitch(profileViewModel)
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                            Button(onClick = { profileViewModel.onEvent(ProfileScreenEvent.SaveButtonOnClick)}) {
                                Text(stringResource(R.string.edit_profile_save_button))
                            }
                        }
                    }
                }
            }
        }
    }
}


