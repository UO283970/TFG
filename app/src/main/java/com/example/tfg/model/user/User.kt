package com.example.tfg.model.user

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.tfg.model.booklist.BookListClass
import com.example.tfg.model.booklist.DefaultList
import com.example.tfg.model.user.userFollowStates.UserFollowStateEnum
import com.example.tfg.model.user.userPrivacy.UserPrivacyLevel
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

class User(
    var userAlias: String,
    var profilePicture: String = "",
    var privacy: UserPrivacyLevel = UserPrivacyLevel.PUBLIC,
    var followState: UserFollowStateEnum = UserFollowStateEnum.NOT_FOLLOW,
    var description: String = "",
    var userName: String = "",
    var numReviews: Int = 0,
    var followers: Int = 0,
    var following: Int = 0,
    var defaultList: List<DefaultList> = arrayListOf<DefaultList>(),
    var userList: List<BookListClass> = arrayListOf<BookListClass>(),
    var userId: String = ""
){

    @Serializable
    data class UserButtonConfig(val buttonTittle: Int, @Contextual val buttonIcon: ImageVector)
}
