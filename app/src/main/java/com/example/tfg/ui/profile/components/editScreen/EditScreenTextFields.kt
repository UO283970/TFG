package com.example.tfg.ui.profile.components.editScreen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.tfg.R
import com.example.tfg.model.AppConstants
import com.example.tfg.ui.profile.ProfileViewModel

@Composable
fun editProfileUserNameTextField(profileViewModel: ProfileViewModel) {
    OutlinedTextField(
        value = profileViewModel.profileEditState.userName,
        onValueChange = { profileViewModel.changeUserName(it) },
        singleLine = true,
        label = { Text(stringResource(R.string.edit_user_name_placeholder)) },
        trailingIcon = {
            if (profileViewModel.profileEditState.userName != "") {
                IconButton(onClick = {
                    profileViewModel.changeUserName(
                        ""
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
fun editProfileUserAliasTextField(profileViewModel: ProfileViewModel) {
    OutlinedTextField(
        value = profileViewModel.profileEditState.userAlias,
        onValueChange = { profileViewModel.changeUserAlias(it) },
        singleLine = true,
        label = { Text(stringResource(R.string.edit_user_alias_placeholder)) },
        trailingIcon = {
            if (profileViewModel.profileEditState.userAlias != "") {
                IconButton(onClick = {
                    profileViewModel.changeUserAlias(
                        ""
                    )
                }) {
                    Icon(
                        Icons.Default.Clear,
                        contentDescription = stringResource(R.string.text_field_delete)
                    )
                }
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun editProfileUserDescriptionTextField(profileViewModel: ProfileViewModel, weight: Modifier) {
    OutlinedTextField(
        value = profileViewModel.profileEditState.userDescription,
        onValueChange = { profileViewModel.changeUserDescription(it) },
        singleLine = false,
        label = { Text(stringResource(R.string.edit_user_description_placeholder)) },
        modifier = Modifier
            .fillMaxWidth()
            .then(weight)
    )
    Text(
        text = profileViewModel.profileEditState.userDescription.length.toString() + " " + stringResource(
            R.string.profile_edit_desc_characters,
            AppConstants.DESC_MAX_CHARACTERS
        ),
        style = MaterialTheme.typography.bodySmall
    )
}