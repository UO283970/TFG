package com.example.tfg.repository.mappers

import com.example.tfg.model.Book
import com.example.tfg.model.booklist.BookListClass
import com.example.tfg.model.booklist.DefaultList
import com.example.tfg.model.booklist.DefaultListNames
import com.example.tfg.model.user.User
import com.example.tfg.model.user.userActivities.ReviewActivity
import com.example.tfg.model.user.userFollowStates.UserFollowStateEnum
import com.example.tfg.model.user.userPrivacy.UserPrivacyLevel
import com.example.tfg.ui.common.StringResourcesProvider
import com.graphQL.GetAllUserInfoQuery
import com.graphQL.GetAllUserInfoQuery.GetAllUserInfo
import com.graphQL.GetAuthenticatedUserInfoQuery
import com.graphQL.GetAuthenticatedUserInfoQuery.UserDefaultList
import com.graphQL.GetAuthenticatedUserInfoQuery.UserList
import com.graphQL.GetFollowersOfUserQuery.GetFollowersOfUser
import com.graphQL.GetFollowingListUserQuery.GetFollowingListUser
import com.graphQL.GetUserSearchInfoQuery.GetUserSearchInfo
import com.graphQL.GetUsersReviewsQuery.GetUsersReview
import java.time.LocalDateTime


fun GetAuthenticatedUserInfoQuery.GetAuthenticatedUserInfo.toUserModel(stringResourcesProvider: StringResourcesProvider): User {
    return User(
        this.userAlias,
        profilePicture = this.profilePictureURL,
        description = this.description,
        userName = this.userName,
        numReviews = this.userActivitiesCount,
        following = this.followingUsersCount,
        followers = this.followedUsersCount,
        defaultList = toBookListFromDefault(this.userDefaultLists, stringResourcesProvider),
        userList = toBookList(this.userLists),
        privacy = UserPrivacyLevel.valueOf(this.userPrivacy.toString())
    )
}


private fun toBookListFromDefault(userDefaultLists: List<UserDefaultList?>, stringResourcesProvider: StringResourcesProvider): List<DefaultList> {
    var appBookList = arrayListOf<DefaultList>()

    for (list in userDefaultLists) {

        appBookList.add(
            DefaultList(
                list?.listId ?: "",
                stringResourcesProvider.getString(DefaultListNames.valueOf(list?.listName.toString()).getDefaultListName()),
                list?.listImage.toString(),
                numberOfBooks = list?.numberOfBooks
                    ?: 0
            )
        )
    }

    return appBookList
}


private fun toBookList(userDefaultLists: List<UserList?>): List<BookListClass> {
    var appBookListClass = arrayListOf<BookListClass>()

    for (list in userDefaultLists) {
        if(list != null){
            appBookListClass.add(BookListClass(list.listId, list.listName, list.listImage))
        }
    }

    return appBookListClass
}

fun List<GetUserSearchInfo>?.toAppSearchUser(): List<User>? {
    var userForSearchApp = arrayListOf<User>()

    if (this != null) {
        for (user in this) {
            userForSearchApp.add(
                User(
                    userAlias = user.userAlias,
                    profilePicture = user.profilePictureURL,
                    userName = user.userName,
                    userId = user.userId
                )
            )
        }
    }

    return userForSearchApp
}

fun GetAllUserInfo?.toUserAppFullInfo(stringResourcesProvider: StringResourcesProvider): User? {
    if (this != null) {
        return User(
            this.userAlias,
            profilePicture = this.profilePictureURL,
            description = this.description,
            userName = this.userName,
            numReviews = this.userActivitiesCount,
            following = this.followingUsersCount,
            followers = this.followedUsersCount,
            defaultList = toBookListUserFromDefault(this.userDefaultLists, stringResourcesProvider, this.userId),
            userList = toUserBookList(this.userLists),
            privacy = UserPrivacyLevel.valueOf(this.userPrivacy.toString()),
            userId = this.userId,
            followState = UserFollowStateEnum.valueOf(this.userFollowState.toString())
        )
    }
    return null
}

private fun toBookListUserFromDefault(
    userDefaultLists: List<GetAllUserInfoQuery.UserDefaultList?>,
    stringResourcesProvider: StringResourcesProvider,
    userId: String
): List<DefaultList> {
    var appBookList = arrayListOf<DefaultList>()

    for (list in userDefaultLists) {
        if(list != null){
            appBookList.add(
                DefaultList(
                    list.listId,
                    stringResourcesProvider.getString(DefaultListNames.valueOf(list.listName.toString()).getDefaultListName()),
                    list.listImage,
                    numberOfBooks = list.numberOfBooks,
                    userId = userId
                )
            )
        }
    }

    return appBookList
}

private fun toUserBookList(userDefaultLists: List<GetAllUserInfoQuery.UserList?>): List<BookListClass> {
    var appBookListClass = arrayListOf<BookListClass>()

    for (list in userDefaultLists) {
        if(list != null){
            appBookListClass.add(BookListClass(list.listId, list.listName, list.listImage))
        }

    }

    return appBookListClass
}


fun List<GetFollowersOfUser>?.userMinInfoFollowers(): List<User>? {
    var followerList = arrayListOf<User>()

    if (this != null) {
        for (user in this) {
            followerList.add(
                User(
                    userAlias = user.userAlias,
                    profilePicture = user.profilePictureURL,
                    followState = UserFollowStateEnum.valueOf(user.userFollowState.toString()),
                    userName = user.userName,
                    userId = user.userId
                )
            )
        }
    }

    return followerList
}

fun List<GetFollowingListUser>?.userMinInfoFollowing(): List<User>? {
    var followerList = arrayListOf<User>()

    if (this != null) {
        for (user in this) {
            followerList.add(
                User(
                    userAlias = user.userAlias,
                    profilePicture = user.profilePictureURL,
                    userName = user.userName,
                    userId = user.userId
                )
            )
        }
    }

    return followerList
}

fun List<GetUsersReview>?.toAppReviews(): List<ReviewActivity>? {
    var listOfAppActivities = arrayListOf<ReviewActivity>()

    if (this != null) {
        for (activity in this) {
            listOfAppActivities.add(
                ReviewActivity(
                    user = User(activity.user.userAlias, profilePicture = activity.user.profilePictureURL, userId = activity.user.userId),
                    creationDate = LocalDateTime.parse(activity.localDateTime).toLocalDate(),
                    book = Book("Palabras Rradiantes", "Brandon  Sanderson"),
                    reviewText = activity.activityText,
                    rating = activity.score
                )
            )
        }
    }

    return listOfAppActivities
}


