package com.example.tfg.ui.profile.components

import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.tfg.ui.profile.ProfileViewModel

@Composable
fun editButton(viewModel: ProfileViewModel) {
    val buttonConfig = viewModel.profileInfo.user.followState.getButtonAction(viewModel.navController)
    Button(onClick = viewModel.profileInfo.user.followState.getButtonAction(viewModel.navController).buttonEvent) {
        Icon(buttonConfig.buttonIcon, contentDescription = "")
        Text(stringResource(buttonConfig.buttonTittle))
    }
}