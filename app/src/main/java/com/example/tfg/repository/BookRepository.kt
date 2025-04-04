package com.example.tfg.repository

import com.apollographql.apollo.ApolloClient
import com.graphQL.SearchBooksQuery
import com.graphQL.SearchBooksQuery.SearchBook
import javax.inject.Inject

class BookRepository @Inject constructor(private val apolloClient: ApolloClient){

    suspend fun searchBooks(userQuery: String): List<SearchBook>? {
        var data = apolloClient.query(SearchBooksQuery(userQuery = userQuery)).execute().data

        return data?.searchBooks
    }

}