package com.example.tfg.model.user.userFollowStates

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Edit
import com.example.tfg.R
import com.example.tfg.model.user.User

enum class UserFollowStateEnum {
    FOLLOWING{
        override fun getButtonAction(): User.UserButtonConfig {
            return User.UserButtonConfig(R.string.user_follow_not_follow,Icons.Outlined.Add) {/*TODO: Enviar solicitud o seguir depende de la privacidad*/}
        }

        override fun getCanShowMoreInfo(): Boolean {
            return false
        }
    },
    FOLLOWED{
        override fun getButtonAction(): User.UserButtonConfig {
            return User.UserButtonConfig(
                R.string.user_follow_follow, Icons.Outlined.Check
            ) {/*TODO: Dejar de seguir*/ }
        }

        override fun getCanShowMoreInfo(): Boolean {
            return true
        }
    },
    OWN{
        override fun getButtonAction(): User.UserButtonConfig {
            return User.UserButtonConfig(R.string.user_follow_own, Icons.Outlined.Edit) {}
        }

        override fun getCanShowMoreInfo(): Boolean {
            return true
        }
    },
    REQUESTED{
        override fun getButtonAction(): User.UserButtonConfig {
            return User.UserButtonConfig(R.string.user_follow_requested, Icons.Outlined.Clear
            ) { /*TODO: Cancelar la solicitud de amistad*/}
        }

        override fun getCanShowMoreInfo(): Boolean {
            return false
        }
    };

    abstract fun getButtonAction(): User.UserButtonConfig
    abstract fun getCanShowMoreInfo(): Boolean
}