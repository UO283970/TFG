package com.example.tfg.model.user.userFollowStates

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Edit
import com.example.tfg.R
import com.example.tfg.model.user.User

enum class UserFollowStateEnum {
    NOT_FOLLOW{
        override fun getButtonAction(): User.UserButtonConfig {
            return User.UserButtonConfig(R.string.user_follow_not_follow,Icons.Outlined.Add)
        }

        override fun getCanShowMoreInfo(): Boolean {
            return false
        }
    },
    FOLLOWING{
        override fun getButtonAction(): User.UserButtonConfig {
            return User.UserButtonConfig(
                R.string.user_follow_follow, Icons.Outlined.Check
            )
        }

        override fun getCanShowMoreInfo(): Boolean {
            return true
        }
    },
    OWN{
        override fun getButtonAction(): User.UserButtonConfig {
            return User.UserButtonConfig(R.string.user_follow_own, Icons.Outlined.Edit)
        }

        override fun getCanShowMoreInfo(): Boolean {
            return true
        }
    },
    REQUESTED{
        override fun getButtonAction(): User.UserButtonConfig {
            return User.UserButtonConfig(R.string.user_follow_requested, Icons.Outlined.Clear
            )
        }

        override fun getCanShowMoreInfo(): Boolean {
            return false
        }
    };

    abstract fun getButtonAction(): User.UserButtonConfig
    abstract fun getCanShowMoreInfo(): Boolean
}