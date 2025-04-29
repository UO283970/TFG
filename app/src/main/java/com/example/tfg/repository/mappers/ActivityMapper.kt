package com.example.tfg.repository.mappers

import com.example.tfg.model.book.Book
import com.example.tfg.model.booklist.DefaultListNames
import com.example.tfg.model.user.User
import com.example.tfg.model.user.userActivities.Activity
import com.example.tfg.model.user.userActivities.RatingActivity
import com.example.tfg.model.user.userActivities.ReviewActivity
import com.example.tfg.ui.common.StringResourcesProvider
import com.graphQL.GetAllFollowedActivityQuery
import com.graphQL.GetAllFollowedActivityQuery.GetAllFollowedActivity
import com.graphQL.GetAllReviewsForBookQuery.GetAllReviewsForBook
import com.graphQL.type.UserActivityType
import java.time.LocalDate
import java.time.LocalDateTime

fun List<GetAllFollowedActivity>?.toAppActivity(stringResourcesProvider: StringResourcesProvider): List<Activity>? {
    var listOfAppActivities = arrayListOf<Activity>()

    if (this != null){
        for (activity in this){
            when(activity.userActivityType){
                UserActivityType.REVIEW -> listOfAppActivities.add(ReviewActivity(
                    user = User(activity.user.userAlias, profilePicture = activity.user.profilePictureURL, userId = activity.user.userId),
                    creationDate =  LocalDateTime.parse(activity.localDateTime).toLocalDate(),
                    book = activity.book.toActivityBook(stringResourcesProvider),
                    reviewText = activity.activityText,
                    rating = activity.score,
                    timeStamp = activity.timestamp
                ))
                UserActivityType.RATING -> listOfAppActivities.add(RatingActivity(
                    user = User(activity.user.userAlias, profilePicture = activity.user.profilePictureURL, userId = activity.user.userId),
                    creationDate =  LocalDateTime.parse(activity.localDateTime).toLocalDate(),
                    book = activity.book.toActivityBook(stringResourcesProvider),
                    rating = activity.score,
                    timeStamp = activity.timestamp
                ))
                UserActivityType.UNKNOWN__ -> ""
            }

        }
    }

    return listOfAppActivities
}

private fun GetAllFollowedActivityQuery.Book.toActivityBook(stringResourcesProvider: StringResourcesProvider): Book {
    return Book(
        this.title,
        this.author,
        this.coverImageURL,
        pages = if(this.pages.isNotBlank()) Integer.valueOf(this.pages) else 0,
        publicationDate =if(this.publishYear.isNotBlank()) LocalDate.ofYearDay(Integer.valueOf(this.publishYear), 12) else LocalDate.MIN,
        bookId = this.bookId,
        readingState = if(DefaultListNames.valueOf(this.readingState.toString()) != DefaultListNames.NOT_IN_LIST)
            stringResourcesProvider.getString(DefaultListNames.valueOf(this.readingState.toString()).getDefaultListName())
        else "",
        subjects = this.subjects,
        details = this.description ?: ""
    )
}


fun List<GetAllReviewsForBook>?.toAppReviews(): List<ReviewActivity>? {
    var listOfAppActivities = arrayListOf<ReviewActivity>()

    if (this != null){
        for (activity in this){
            listOfAppActivities.add(ReviewActivity(
                user = User(activity.user.userAlias, profilePicture = activity.user.profilePictureURL, userId = activity.user.userId),
                creationDate =  LocalDateTime.parse(activity.localDateTime).toLocalDate(),
                book = Book("",""),
                reviewText = activity.activityText,
                rating = activity.score,
                timeStamp = activity.timestamp
            ))
        }
    }

    return listOfAppActivities
}