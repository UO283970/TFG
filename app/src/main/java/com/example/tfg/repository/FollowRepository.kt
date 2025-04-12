package com.example.tfg.repository

import com.apollographql.apollo.ApolloClient
import com.graphQL.AcceptRequestMutation
import com.graphQL.CancelFollowMutation
import com.graphQL.CancelRequestMutation
import com.graphQL.DeleteFromFollowerMutation
import com.graphQL.FollowUserMutation
import com.graphQL.type.UserFollowState
import javax.inject.Inject

class FollowRepository @Inject constructor(val apolloClient: ApolloClient) {

    suspend fun followUser(friendId: String): UserFollowState? {
        return apolloClient.mutation(FollowUserMutation(friendId)).execute().data?.followUser
    }

    suspend fun cancelFollow(friendId: String): Boolean? {
        return apolloClient.mutation(CancelFollowMutation(friendId)).execute().data?.cancelFollow
    }

    suspend fun acceptRequest(friendId: String): Boolean? {
        return apolloClient.mutation(AcceptRequestMutation(friendId)).execute().data?.acceptRequest
    }

    suspend fun cancelRequest(friendId: String): Boolean? {
        return apolloClient.mutation(CancelRequestMutation(friendId)).execute().data?.cancelRequest
    }

    suspend fun deleteFromFollower(friendId: String): Boolean? {
        return apolloClient.mutation(DeleteFromFollowerMutation(friendId)).execute().data?.deleteFromFollower
    }

}