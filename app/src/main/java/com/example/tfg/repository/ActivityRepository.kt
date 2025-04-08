package com.example.tfg.repository

import com.apollographql.apollo.ApolloClient
import com.example.tfg.model.user.userActivities.Activity
import com.example.tfg.repository.mappers.toAppActivity
import com.graphQL.AddActivityMutation
import com.graphQL.DeleteActivityMutation
import com.graphQL.GetAllFollowedActivityQuery
import com.graphQL.UpdateActivityMutation
import com.graphQL.type.UserActivityType
import javax.inject.Inject

class ActivityRepository @Inject constructor(private val apolloClient: ApolloClient) {

    suspend fun addActivity(activityText: String, score: Int, bookId: String, userActivityType: UserActivityType): Boolean? {
        return apolloClient.mutation(AddActivityMutation(activityText = activityText, score = score, bookId = bookId, userActivityType = userActivityType))
            .execute().data?.addActivity
    }

    suspend fun deleteActivity(activityId: String): Boolean? {
        return apolloClient.mutation(DeleteActivityMutation(activityId = activityId))
            .execute().data?.deleteActivity
    }

    suspend fun updateActivity(activityId: String, activityText : String, score: Int): Boolean? {
        return apolloClient.mutation(UpdateActivityMutation(activityId = activityId, activityText = activityText, score = score))
            .execute().data?.updateActivity
    }

    suspend fun getAllFollowedActivity(): List<Activity>? {
        return apolloClient.query(GetAllFollowedActivityQuery())
            .execute().data?.getAllFollowedActivity.toAppActivity()
    }

}


