package com.example.tfg.repository

import com.apollographql.apollo.ApolloClient
import com.example.tfg.model.book.Book
import com.example.tfg.repository.mappers.toAppBooksFromPages
import com.example.tfg.repository.mappers.toBookFromSearch
import com.example.tfg.ui.common.StringResourcesProvider
import com.graphQL.GetExtraInfoForBookQuery
import com.graphQL.NextPageBooksQuery
import com.graphQL.SearchBooksQuery
import javax.inject.Inject

class BookRepository @Inject constructor(private val apolloClient: ApolloClient, val stringResourcesProvider: StringResourcesProvider) {

    suspend fun searchBooks(userQuery: String, searchFor: String, subject: String): List<Book>? {
        var data = apolloClient.query(SearchBooksQuery(userQuery = userQuery, searchFor = searchFor, subject = subject)).execute().data

        return data?.searchBooks.toBookFromSearch(stringResourcesProvider)
    }

    suspend fun nextPageBooks(userQuery: String, page: Int, searchFor: String, subject: String): List<Book>? {
        var data = apolloClient.query(NextPageBooksQuery(userQuery = userQuery, page = page, searchFor = searchFor, subject = subject)).execute().data

        return data?.nextPageBooks.toAppBooksFromPages(stringResourcesProvider)
    }

    suspend fun getExtraInfoForBook(bookId: String): GetExtraInfoForBookQuery.GetExtraInfoForBook? {
        var data = apolloClient.query(GetExtraInfoForBookQuery(bookId = bookId)).execute().data
        return data?.getExtraInfoForBook
    }

}


