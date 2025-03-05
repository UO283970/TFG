package com.example.tfg.model

import java.time.LocalDate

enum class userFollow {
    NOTFOLLOW,
    FOLLOW,
    OWN,
    REQUESTED
}

enum class userPrivacy {
    PRIVATE,
    PUBLIC
}

class User(
    var userName: String,
    var profilePicture: String,
    var userFollow: userFollow,
    var privacy: userPrivacy,
    var joinYear: LocalDate = LocalDate.MIN,
    var description: String = "",
    numRatings : Int = 0,
    numReviews : Int = 0,
    followers : Int = 0,
    followed : Int = 0,
    ) {
    var numRatings : Int = if (numRatings >= 0) numRatings else 0
    var numReviews : Int = if (numReviews >= 0) numReviews else 0
    var followers : Int = if (followers >= 0) followers else 0
    var followed : Int = if (followed >= 0) followed else 0
}
