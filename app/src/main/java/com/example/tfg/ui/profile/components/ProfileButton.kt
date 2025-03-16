package com.example.tfg.ui.profile.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.tfg.model.user.User
import com.example.tfg.model.user.userFollowStates.UserFollowStateEnum
import com.example.tfg.ui.common.navHost.ProfileNavigationItems

@Composable
fun EditButton(user: User?, navigateTo : (route: String) -> Unit) {
    val buttonConfig = user?.followState?.getButtonAction()

    if(user?.followState == UserFollowStateEnum.OWN){
        Button(onClick = { navigateTo(ProfileNavigationItems.EditProfile.route) }) {
            Icon(Icons.Outlined.Edit, contentDescription = "")
            Text(stringResource(buttonConfig?.buttonTittle ?: 0))
        }
    }else{
        Button(onClick = user?.followState?.getButtonAction()?.buttonEvent ?: {}) {
            Icon(buttonConfig?.buttonIcon ?: Icons.Default.Add, contentDescription = "")
            Text(stringResource(buttonConfig?.buttonTittle ?: 0))
        }
    }
}