package com.example.tfg.ui.profile.components

import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.tfg.ui.profile.ProfileScreenEvent
import com.example.tfg.ui.profile.ProfileViewModel

@Composable
fun editButton(viewModel: ProfileViewModel) {
    Button(onClick = { viewModel.onEvent(ProfileScreenEvent.EditButtonClick) }) {
        Icon(viewModel.profileInfo.user.getFollowStateInfo().buttonIcon, contentDescription = "")
        Text(stringResource(viewModel.profileInfo.user.getFollowStateInfo().buttonTittle))
    }
}