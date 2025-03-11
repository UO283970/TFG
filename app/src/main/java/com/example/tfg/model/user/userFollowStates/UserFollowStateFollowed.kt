package com.example.tfg.model.user.userFollowStates

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import com.example.tfg.R
import com.example.tfg.model.user.User
import com.example.tfg.model.user.UserFollowState
import com.example.tfg.ui.profile.ProfileScreenEvent

class UserFollowStateFollowed: UserFollowState {
    override fun getButtonAction(): User.UserButtonConfig {
        return User.UserButtonConfig(R.string.user_follow_requested, Icons.Outlined.Check,ProfileScreenEvent.UnFollowButtonClick)
    }

    override fun getCanShowMoreInfo(): Boolean {
        return true
    }
}