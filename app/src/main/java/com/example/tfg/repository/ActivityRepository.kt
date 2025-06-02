package com.example.tfg.repository

import com.apollographql.apollo.ApolloClient
import com.example.tfg.model.user.userActivities.Activity
import com.example.tfg.model.user.userActivities.ReviewActivity
import com.example.tfg.repository.mappers.toAppActivity
import com.example.tfg.repository.mappers.toAppReviews
import com.example.tfg.ui.common.StringResourcesProvider
import com.graphQL.AddActivityMutation
import com.graphQL.DeleteActivityMutation
import com.graphQL.GetAllFollowedActivityQuery
import com.graphQL.GetAllReviewsForBookQuery
import com.graphQL.type.UserActivityType
import javax.inject.Inject

class ActivityRepository @Inject constructor(private val apolloClient: ApolloClient, private val stringResourcesProvider: StringResourcesProvider) {

    suspend fun addActivity(activityText: String, score: Int, bookId: String, userActivityType: UserActivityType): Boolean? {
        return apolloClient.mutation(AddActivityMutation(activityText = activityText, score = score, bookId = bookId, userActivityType = userActivityType))
            .execute().data?.addActivity
    }

    suspend fun deleteActivity(activityId: String): Boolean? {
        return apolloClient.mutation(DeleteActivityMutation(activityId = activityId))
            .execute().data?.deleteActivity
    }

    suspend fun getAllFollowedActivity(timestamp: String): List<Activity>? {
        return apolloClient.query(GetAllFollowedActivityQuery(timestamp))
            .execute().data?.getAllFollowedActivity.toAppActivity(stringResourcesProvider)
    }

    suspend fun getAllReviewsForBook(bookId: String): List<ReviewActivity>? {
        return apolloClient.query(GetAllReviewsForBookQuery(bookId))
            .execute().data?.getAllReviewsForBook.toAppReviews()
    }

}



