package com.example.tfg.model.user.userFollowStates

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import com.example.tfg.R
import com.example.tfg.model.user.User
import com.example.tfg.model.user.UserFollowState

class UserFollowStateUnfollow: UserFollowState {
    override fun getButtonAction(): User.UserButtonConfig {
        return User.UserButtonConfig(R.string.user_follow_not_follow,Icons.Outlined.Add,{})
    }

    override fun getCanShowMoreInfo(): Boolean {
        return false
    }
}