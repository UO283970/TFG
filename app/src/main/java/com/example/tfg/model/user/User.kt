package com.example.tfg.model.user

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.tfg.model.user.userFollowStates.UserFollowStateUnfollow
import com.example.tfg.model.user.userPrivacy.UserPrivacyPrivate
import com.example.tfg.ui.profile.ProfileScreenEvent
import java.time.LocalDate


class User(
    var userAlias: String,
    var profilePicture: Int = 0,
    var privacy: UserPrivacy = UserPrivacyPrivate(),
    var followState: UserFollowState = UserFollowStateUnfollow(),
    var joinYear: LocalDate = LocalDate.MIN,
    var description: String = "",
    var userName: String = "",
    var numRatings: Int = 0,
    var numReviews: Int = 0,
    var followers: Int = 0,
    var followed: Int = 0,
) {
    fun getFollowStateInfo(): UserButtonConfig {
        return followState.getButtonAction()
    }

    fun getShowMoreInfo():Boolean{
        return privacy.getShowMainInfo() || followState.getCanShowMoreInfo()
    }
    data class UserButtonConfig(val buttonTittle: Int, val buttonIcon: ImageVector, val buttonEvent: ProfileScreenEvent)
}
