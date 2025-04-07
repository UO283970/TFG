package com.example.tfg.model.user

import android.os.Parcelable
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.tfg.model.booklist.BookList
import com.example.tfg.model.booklist.DefaultList
import com.example.tfg.model.user.userFollowStates.UserFollowStateEnum
import com.example.tfg.model.user.userPrivacy.UserPrivacyLevel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
class User(
    var userAlias: String,
    var profilePicture: Int = 0,
    var privacy: UserPrivacyLevel = UserPrivacyLevel.PUBLIC,
    var followState: UserFollowStateEnum = UserFollowStateEnum.FOLLOW,
    var description: String = "",
    var userName: String = "",
    var numReviews: Int = 0,
    var followers: Int = 0,
    var following: Int = 0,
    var defaultList: List<DefaultList> = arrayListOf<DefaultList>(),
    var userList: List<BookList> = arrayListOf<BookList>(),
) : Parcelable {

    @Serializable
    data class UserButtonConfig(val buttonTittle: Int, @Contextual val buttonIcon: ImageVector, val buttonEvent: () -> Unit)
}
