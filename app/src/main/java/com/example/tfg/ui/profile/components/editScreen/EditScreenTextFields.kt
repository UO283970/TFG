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

@Composable
fun EditProfileUserNameTextField(editProfileViewModel: EditProfileViewModel) {
    OutlinedTextField(
        value = editProfileViewModel.profileEditState.userName,
        onValueChange = { editProfileViewModel.changeUserName(it) },
        singleLine = true,
        label = { Text(stringResource(R.string.edit_user_name_placeholder)) },
        trailingIcon = {
            if (editProfileViewModel.profileEditState.userName != "") {
                IconButton(onClick = {
                    editProfileViewModel.changeUserName(
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
        isError = editProfileViewModel.profileEditState.userNameError != null,
    )
    if (editProfileViewModel.profileEditState.userNameError != null)
        Text(
            text = editProfileViewModel.profileEditState.userNameError!!,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall
        )
}

@Composable
fun EditProfileUserAliasTextField(editProfileViewModel: EditProfileViewModel) {
    OutlinedTextField(
        value = editProfileViewModel.profileEditState.userAlias,
        onValueChange = { editProfileViewModel.changeUserAlias(it) },
        singleLine = true,
        label = { Text(stringResource(R.string.edit_user_alias_placeholder)) },
        trailingIcon = {
            if (editProfileViewModel.profileEditState.userAlias != "") {
                IconButton(onClick = {
                    editProfileViewModel.changeUserAlias(
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
fun EditProfileUserDescriptionTextField(editProfileViewModel: EditProfileViewModel, modifier: Modifier) {
    OutlinedTextField(
        value = editProfileViewModel.profileEditState.userDescription,
        onValueChange = { editProfileViewModel.changeUserDescription(it) },
        singleLine = false,
        label = { Text(stringResource(R.string.edit_user_description_placeholder)) },
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    )
    Text(
        text = editProfileViewModel.profileEditState.userDescription.length.toString() + " " + stringResource(
            R.string.profile_edit_desc_characters,
            AppConstants.DESC_MAX_CHARACTERS
        ),
        style = MaterialTheme.typography.bodySmall
    )
}