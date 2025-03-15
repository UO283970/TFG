package com.example.tfg.model.user

import android.os.Parcelable
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.tfg.model.user.userFollowStates.UserFollowStateEnum
import com.example.tfg.model.user.userPrivacy.UserPrivacyLevel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
@Parcelize
class User(
    var userAlias: String,
    var profilePicture: Int = 0,
    var privacy: UserPrivacyLevel = UserPrivacyLevel.PUBLIC,
    var followState: UserFollowStateEnum = UserFollowStateEnum.FOLLOW,
    @Serializable(LocalDateSerializer::class)
    var joinYear: LocalDate = LocalDate.MIN,
    var description: String = "",
    var userName: String = "",
    var numRatings: Int = 0,
    var numReviews: Int = 0,
    var followers: Int = 0,
    var followed: Int = 0,
) : Parcelable {

    fun getShowMoreInfo():Boolean{
        return privacy.getMoreInfo() || followState.getCanShowMoreInfo()
    }

    @Serializable
    data class UserButtonConfig(val buttonTittle: Int, @Contextual val buttonIcon: ImageVector, val buttonEvent: () -> Unit)
}
