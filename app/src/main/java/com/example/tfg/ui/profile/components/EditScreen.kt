package com.example.tfg.ui.profile.components

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.tfg.R
import com.example.tfg.ui.profile.ProfileScreenEvent
import com.example.tfg.ui.profile.ProfileViewModel
import com.example.tfg.ui.theme.TFGTheme

class EditActivity(private val profileViewModel: ProfileViewModel) : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TFGTheme(dynamicColor = false)
            {
                Scaffold(
                    topBar = {
//                        topDetailsListBar(
//                            commonEventHandler,
//                            tittle = stringResource(R.string.profile_rating_text)
//                        )
                    }
                ) { innerPadding ->
                    Column(
                        Modifier
                            .padding(innerPadding)
                    ) {
                        HorizontalDivider()
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(
                                painterResource(profileViewModel.profileInfo.user.profilePicture),
                                contentDescription = "",
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier
                                    .clip(CircleShape)
                            )
                            Text(
                                stringResource(R.string.edit_change_profile_image),
                                modifier = Modifier.clickable { /*TODO: Se cambia la foto de perfil*/ })
                        }
                        Column {
                            editProfileUserNameTextField()
                            editProfileUserAliasTextField()
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun editProfileUserNameTextField() {
        OutlinedTextField(
            value = profileViewModel.profileEditState.userName,
            onValueChange = { profileViewModel.onEvent(ProfileScreenEvent.ChangeUserName(it)) },
            singleLine = true,
            label = { Text(stringResource(R.string.login_user_placeholder)) },
            trailingIcon = {
                if (profileViewModel.profileEditState.userName != "") {
                    IconButton(onClick = {
                        profileViewModel.onEvent(
                            ProfileScreenEvent.ChangeUserName(
                                ""
                            )
                        )
                    }) {
                        Icon(
                            Icons.Default.Clear,
                            contentDescription = stringResource(R.string.text_field_delete)
                        )
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            isError = profileViewModel.profileEditState.userNameError != null,
        )
        if (profileViewModel.profileEditState.userNameError != null)
            Text(
                text = profileViewModel.profileEditState.userNameError!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
    }

    @Composable
    private fun editProfileUserAliasTextField() {
        OutlinedTextField(
            value = profileViewModel.profileEditState.userAlias,
            onValueChange = { profileViewModel.onEvent(ProfileScreenEvent.ChangeUserAlias(it)) },
            singleLine = true,
            label = { Text(stringResource(R.string.login_user_placeholder)) },
            trailingIcon = {
                if (profileViewModel.profileEditState.userAlias != "") {
                    IconButton(onClick = {
                        profileViewModel.onEvent(
                            ProfileScreenEvent.ChangeUserAlias(
                                ""
                            )
                        )
                    }) {
                        Icon(
                            Icons.Default.Clear,
                            contentDescription = stringResource(R.string.text_field_delete)
                        )
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            isError = profileViewModel.profileEditState.userAliasError != null,
        )
        if (profileViewModel.profileEditState.userAliasError != null)
            Text(
                text = profileViewModel.profileEditState.userAliasError!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
    }
}