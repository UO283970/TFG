package com.example.tfg.repository

import com.apollographql.apollo.api.ApolloRequest
import com.apollographql.apollo.api.ApolloResponse
import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.interceptor.ApolloInterceptor
import com.apollographql.apollo.interceptor.ApolloInterceptorChain
import com.example.tfg.repository.exceptions.AuthenticationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ErrorInterceptor : ApolloInterceptor{
    override fun <D : Operation.Data> intercept(
        request: ApolloRequest<D>,
        chain: ApolloInterceptorChain
    ): Flow<ApolloResponse<D>> {
        chain.proceed(request).map {
            response ->
            if(response.hasErrors()){
                val error = response.errors?.first()
                val errorType = error?.extensions?.get("error_type")?.equals(AppErrors.AUTHENTICATION_ERROR)

                if(errorType == true) {
                    throw AuthenticationException()
                }

            }
        }

        return  chain.proceed(request)
    }
}

private enum class AppErrors{
    AUTHENTICATION_ERROR,
    UNKNOWN_ERROR
}
