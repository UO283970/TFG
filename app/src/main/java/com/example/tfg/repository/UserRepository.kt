package com.example.tfg.repository

import com.apollographql.apollo.ApolloClient
import com.example.tfg.model.user.User
import com.example.tfg.model.user.userActivities.ReviewActivity
import com.example.tfg.repository.mappers.toAppReviews
import com.example.tfg.repository.mappers.toAppSearchUser
import com.example.tfg.repository.mappers.toUserAppFullInfo
import com.example.tfg.repository.mappers.toUserModel
import com.example.tfg.repository.mappers.userMinInfoFollowers
import com.example.tfg.repository.mappers.userMinInfoFollowing
import com.example.tfg.ui.common.StringResourcesProvider
import com.graphQL.CreateUserMutation
import com.graphQL.CreateUserMutation.CreateUser
import com.graphQL.DeleteUserMutation
import com.graphQL.GetAllUserInfoQuery
import com.graphQL.GetAuthenticatedUserInfoQuery
import com.graphQL.GetFollowersOfUserQuery
import com.graphQL.GetFollowingListUserQuery
import com.graphQL.GetUserSearchInfoQuery
import com.graphQL.GetUsersReviewsQuery
import com.graphQL.LoginQuery
import com.graphQL.LoginQuery.Login
import com.graphQL.LogoutQuery
import com.graphQL.RefreshTokenQuery
import com.graphQL.UpdateUserMutation
import com.graphQL.type.UserPrivacy
import javax.inject.Inject

class UserRepository @Inject constructor(private val apolloClient: ApolloClient, private val stringResourcesProvider: StringResourcesProvider) {

    suspend fun createUser(
        email: String,
        password: String,
        repeatedPassword: String,
        userAlias: String,
        userName: String,
        profilePictureURL: String
    ): CreateUser? {
        return apolloClient.mutation(
            CreateUserMutation(
                email = email,
                password = password,
                repeatedPassword = repeatedPassword,
                userAlias = userAlias,
                userName = userName,
                profilePictureURL = profilePictureURL
            )
        ).execute().data?.createUser
    }


    suspend fun updateUser(userAlias: String, userName: String, profilePictureURL: String, description: String, privacy: UserPrivacy): Boolean? {
        return apolloClient.mutation(
            UpdateUserMutation(
                userAlias = userAlias,
                userName = userName,
                profilePictureURL = profilePictureURL, description = description,
                privacyLevel = privacy
            )
        ).execute().data?.updateUser
    }

    suspend fun deleteUser(): Boolean? {
        return apolloClient.mutation(
            DeleteUserMutation()
        ).execute().data?.deleteUser
    }

    suspend fun login(email: String, password: String): Login? {
        val response = apolloClient.query(
            LoginQuery(email = email, password = password)
        ).execute()

        if (response.hasErrors()) {
            return null
        }

        return apolloClient.query(
            LoginQuery(email = email, password = password)
        ).execute().data?.login
    }

    suspend fun logout(): String? {
        return apolloClient.query(
            LogoutQuery()
        ).execute().data?.logout
    }

    suspend fun refreshToken(oldRefreshToken: String): String? {
        return apolloClient.query(
            RefreshTokenQuery(oldRefreshToken = oldRefreshToken)
        ).execute().data?.refreshToken
    }

    suspend fun getUserSearchInfo(userQuery: String): List<User>? {
        return apolloClient.query(
            GetUserSearchInfoQuery(userQuery = userQuery)
        ).execute().data?.getUserSearchInfo.toAppSearchUser()
    }

    suspend fun getAuthenticatedUserInfo(): User? {
        return apolloClient.query(
            GetAuthenticatedUserInfoQuery()
        ).execute().data?.getAuthenticatedUserInfo?.toUserModel(stringResourcesProvider)
    }

    suspend fun getAllUserInfo(userId: String): User? {
        return apolloClient.query(
            GetAllUserInfoQuery(userId = userId)
        ).execute().data?.getAllUserInfo.toUserAppFullInfo(stringResourcesProvider)
    }

    suspend fun getFollowersOfUser(userId: String): List<User>? {
        return apolloClient.query(
            GetFollowersOfUserQuery(userId = userId)
        ).execute().data?.getFollowersOfUser.userMinInfoFollowers()
    }

    suspend fun getFollowingListUser(userId: String): List<User>? {
        return apolloClient.query(
            GetFollowingListUserQuery(userId = userId)
        ).execute().data?.getFollowingListUser.userMinInfoFollowing()
    }

    suspend fun getUsersReviews(userId: String): List<ReviewActivity>? {
        return apolloClient.query(
            GetUsersReviewsQuery(userId = userId)
        ).execute().data?.getUsersReviews.toAppReviews()
    }

}


