package com.example.tfg.repository

import com.apollographql.apollo.ApolloClient
import com.example.tfg.model.booklist.BookList
import com.example.tfg.model.booklist.DefaultList
import com.example.tfg.repository.mappers.toAppLists
import com.example.tfg.repository.mappers.toDefaultAppLists
import com.graphQL.AddBookToDefaultListMutation
import com.graphQL.AddBookToListMutation
import com.graphQL.CreateListMutation
import com.graphQL.DeleteListMutation
import com.graphQL.GetAllListInfoQuery
import com.graphQL.GetAllListInfoQuery.GetAllListInfo
import com.graphQL.GetBasicListInfoListQuery
import com.graphQL.GetUserDefaultListQuery
import com.graphQL.GetUserDefaultListQuery.GetUserDefaultList
import com.graphQL.GetUserDefaultListsListQuery
import com.graphQL.RemoveBookFromDefaultListMutation
import com.graphQL.RemoveBookFromListMutation
import com.graphQL.SearchListsQuery
import com.graphQL.SearchListsQuery.SearchList
import com.graphQL.UpdateListMutation
import com.graphQL.type.BookListPrivacy
import com.graphQL.type.ReadingState
import javax.inject.Inject

class ListRepository @Inject constructor(private val apolloClient: ApolloClient) {

    suspend fun createList(listName: String, description: String, bookListPrivacy: BookListPrivacy): String? {
        return apolloClient.mutation(CreateListMutation(listName = listName, description = description, bookListPrivacy = bookListPrivacy))
            .execute().data?.createList
    }

    suspend fun updateList(listId: String, listName: String, description: String, bookListPrivacy: BookListPrivacy): Boolean? {
        return apolloClient.mutation(
            UpdateListMutation(
                listName = listName, description = description, bookListPrivacy = bookListPrivacy,
                listId = listId
            )
        ).execute().data?.updateList
    }

    suspend fun deleteList(listId: String): Boolean? {
        return apolloClient.mutation(
            DeleteListMutation(
                listId = listId
            )
        ).execute().data?.deleteList
    }

    suspend fun addBookToDefaultList(listId: String, bookId: String): ReadingState? {
        return apolloClient.mutation(
            AddBookToDefaultListMutation(
                listId = listId, bookId = bookId
            )
        ).execute().data?.addBookToDefaultList
    }

    suspend fun removeBookFromDefaultList(listId: String, bookId: String): Boolean? {
        return apolloClient.mutation(
            RemoveBookFromDefaultListMutation(
                listId = listId, bookId = bookId
            )
        ).execute().data?.removeBookFromDefaultList
    }

    suspend fun addBookToList(listId: String, bookId: String): Boolean? {
        return apolloClient.mutation(
            AddBookToListMutation(
                listId = listId, bookId = bookId
            )
        ).execute().data?.addBookToList
    }

    suspend fun removeBookFromList(listId: String, bookId: String): Boolean? {
        return apolloClient.mutation(
            RemoveBookFromListMutation(
                listId = listId, bookId = bookId
            )
        ).execute().data?.removeBookFromList
    }

    suspend fun getBasicListInfo(userId: String): List<BookList>? {
        return apolloClient.query(GetBasicListInfoListQuery(userId = userId)).execute().data?.getBasicListInfoList.toAppLists()
    }

    suspend fun getAllListInfo(listId: String): GetAllListInfo? {
        return apolloClient.query(GetAllListInfoQuery(listId = listId)).execute().data?.getAllListInfo
    }

    suspend fun getUserDefaultList(listId: String, userId: String): GetUserDefaultList? {
        return apolloClient.query(GetUserDefaultListQuery(listId = listId, userId = userId)).execute().data?.getUserDefaultList
    }

    suspend fun getUserDefaultLists(userId: String): List<DefaultList>? {
        return apolloClient.query(GetUserDefaultListsListQuery(userId = userId)).execute().data?.getUserDefaultListsList.toDefaultAppLists()
    }

    suspend fun searchLists(userQuery: String): List<SearchList>? {
        return apolloClient.query(SearchListsQuery(userQuery = userQuery)).execute().data?.searchLists
    }

}

