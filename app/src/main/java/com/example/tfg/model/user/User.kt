package com.example.tfg.model.user

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.tfg.model.user.userFollowStates.UserFollowStateUnfollow
import com.example.tfg.model.user.userPrivacy.UserPrivacyPrivate
import java.time.LocalDate

class User(
    var userName: String,
    var profilePicture: Int = 0,
    var privacy: UserPrivacy = UserPrivacyPrivate(),
    var followState: UserFollowState = UserFollowStateUnfollow(),
    var joinYear: LocalDate = LocalDate.MIN,
    var description: String = "",
    numRatings: Int = 0,
    numReviews: Int = 0,
    followers: Int = 0,
    followed: Int = 0,
) {
    var numRatings: Int = if (numRatings >= 0) numRatings else 0
    var numReviews: Int = if (numReviews >= 0) numReviews else 0
    var followers: Int = if (followers >= 0) followers else 0
    var followed: Int = if (followed >= 0) followed else 0

    fun getFollowStateInfo(): UserButtonConfig {
        return followState.getButtonAction()
    }

    fun getShowMoreInfo():Boolean{
        return privacy.getShowMainInfo() || followState.getCanShowMoreInfo()
    }
    data class UserButtonConfig(val buttonTittle: Int, val buttonIcon: ImageVector, val buttonOnClick: () -> Unit)
}
