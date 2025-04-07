package com.example.tfg.repository.mappers

import com.example.tfg.R
import com.example.tfg.model.booklist.BookList
import com.example.tfg.model.booklist.DefaultList
import com.example.tfg.model.booklist.DefaultListNames
import com.example.tfg.model.user.User
import com.graphQL.GetAuthenticatedUserInfoQuery
import com.graphQL.GetAuthenticatedUserInfoQuery.UserDefaultList
import com.graphQL.GetAuthenticatedUserInfoQuery.UserList


fun GetAuthenticatedUserInfoQuery.GetAuthenticatedUserInfo.toUserModel(): User {
    return User(
        this.userName,
        profilePicture = R.drawable.prueba,
        description = this.description,
        userName = this.userName,
        numReviews = this.userActivitiesCount,
        following = this.followingUsersCount,
        followers = this.followedUsersCount,
        defaultList = toBookListFromDefault(this.userDefaultLists),
        userList = toBookList(this.userLists)
    )
}


private fun toBookListFromDefault(userDefaultLists: List<UserDefaultList?>): List<DefaultList> {
    var appBookList = arrayListOf<DefaultList>()

    for (list in userDefaultLists) {

        appBookList.add(
            DefaultList(
                list?.listId ?: "",
                DefaultListNames.valueOf(list?.listName.toString()).getDefaultListName(),
                R.drawable.prueba,
                numberOfBooks = list?.numberOfBooks
                    ?: 0
            )
        )
    }

    return appBookList
}


private fun toBookList(userDefaultLists: List<UserList?>): List<BookList> {
    var appBookList = arrayListOf<BookList>()

    for (list in userDefaultLists) {
        appBookList.add(BookList(list?.listId ?: "", list?.listName ?: "", R.drawable.prueba))
    }

    return appBookList
}



