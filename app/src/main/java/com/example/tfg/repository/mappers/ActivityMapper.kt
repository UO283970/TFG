package com.example.tfg.repository.mappers

import com.example.tfg.model.Book
import com.example.tfg.model.user.User
import com.example.tfg.model.user.userActivities.Activity
import com.example.tfg.model.user.userActivities.RatingActivity
import com.example.tfg.model.user.userActivities.ReviewActivity
import com.graphQL.GetAllFollowedActivityQuery.GetAllFollowedActivity
import com.graphQL.type.UserActivityType
import java.time.LocalDateTime

fun List<GetAllFollowedActivity>?.toAppActivity(): List<Activity>? {
    var listOfAppActivities = arrayListOf<Activity>()

    if (this != null){
        for (activity in this){
            when(activity.userActivityType){
                UserActivityType.REVIEW -> listOfAppActivities.add(ReviewActivity(
                    user = User(activity.user.userAlias, profilePicture = activity.user.profilePictureURL, userId = activity.user.userId),
                    creationDate =  LocalDateTime.parse(activity.localDateTime).toLocalDate(),
                    book = Book("Palabras Rradiantes","Brandon  Sanderson"),
                    reviewText = activity.activityText,
                    rating = activity.score
                ))
                UserActivityType.RATING -> listOfAppActivities.add(RatingActivity(
                    user = User(activity.user.userAlias, profilePicture = activity.user.profilePictureURL, userId = activity.user.userId),
                    creationDate =  LocalDateTime.parse(activity.localDateTime).toLocalDate(),
                    book = Book("Palabras Rradiantes","Brandon  Sanderson"),
                    rating = activity.score
                ))
                UserActivityType.UNKNOWN__ -> ""
            }

        }
    }

    return listOfAppActivities
}