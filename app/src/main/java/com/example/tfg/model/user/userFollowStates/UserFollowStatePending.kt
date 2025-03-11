package com.example.tfg.model.user.userFollowStates

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import com.example.tfg.R
import com.example.tfg.model.user.User
import com.example.tfg.model.user.UserFollowState
import com.example.tfg.ui.profile.ProfileScreenEvent

class UserFollowStatePending: UserFollowState {
    override fun getButtonAction(): User.UserButtonConfig {
        return User.UserButtonConfig(R.string.user_follow_follow, Icons.Outlined.Clear,ProfileScreenEvent.PendingButtonClick)
    }

    override fun getCanShowMoreInfo(): Boolean {
        return false
    }
}