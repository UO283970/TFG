package com.example.tfg.model

import com.example.tfg.R
import java.time.LocalDate

enum class UserFollow {
    `NOT-FOLLOW`,
    FOLLOW,
    OWN,
    REQUESTED
}

enum class UserPrivacy {
    PRIVATE,
    PUBLIC
}

class User(
    var userName: String,
    var profilePicture: Int,
    var userFollow: UserFollow,
    var privacy: UserPrivacy,
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

    fun getUserFollow(): Int {
        when (userFollow) {
            UserFollow.FOLLOW -> {
                return R.string.user_follow_follow
            }
            UserFollow.`NOT-FOLLOW` -> {
                return R.string.user_follow_not_follow
            }
            UserFollow.REQUESTED -> {
                return R.string.user_follow_requested
            }
            UserFollow.OWN -> {
                return R.string.user_follow_own
            }
        }
    }
}
