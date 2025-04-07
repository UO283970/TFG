package com.example.tfg.repository

import com.apollographql.apollo.api.ApolloRequest
import com.apollographql.apollo.api.ApolloResponse
import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.interceptor.ApolloInterceptor
import com.apollographql.apollo.interceptor.ApolloInterceptorChain
import kotlinx.coroutines.flow.Flow

class AuthorizationInterceptor(private val tokenProvider: () -> String?) : ApolloInterceptor {
    override fun <D : Operation.Data> intercept(
        request: ApolloRequest<D>,
        chain: ApolloInterceptorChain
    ): Flow<ApolloResponse<D>> {
        val operationName = request.operation.name()

        val unauthenticatedOperations = listOf(
            "Login", "CreateUser", "RefreshToken"
        )

        val token = tokenProvider()

        val shouldAddToken = !token.isNullOrEmpty() && !unauthenticatedOperations.contains(operationName)

        val modifiedRequest = if (shouldAddToken) {
            request.newBuilder()
                .addHttpHeader("Authorization", "Bearer $token")
                .build()
        } else {
            request
        }

        return chain.proceed(modifiedRequest)
    }
}