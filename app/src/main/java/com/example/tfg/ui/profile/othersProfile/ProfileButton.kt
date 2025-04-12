package com.example.tfg.ui.profile.othersProfile

import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.tfg.model.user.userFollowStates.UserFollowStateEnum
import com.example.tfg.ui.common.navHost.ProfileNavigationItems

@Composable
fun ProfileButton(followState: UserFollowStateEnum, navigateTo: (route: String) -> Unit, cancelFollow: () -> Unit, followUser: () -> Unit){
    Button({
        when(followState) {
            UserFollowStateEnum.NOT_FOLLOW -> followUser()
            UserFollowStateEnum.FOLLOWING -> cancelFollow()
            UserFollowStateEnum.OWN -> navigateTo(ProfileNavigationItems.EditProfile.route)
            UserFollowStateEnum.REQUESTED -> cancelFollow()
        }
    }){
        Icon(followState.getButtonAction().buttonIcon,null)
        Text(stringResource(followState.getButtonAction().buttonTittle))
    }
}

