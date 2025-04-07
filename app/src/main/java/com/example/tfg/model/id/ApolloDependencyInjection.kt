package com.example.tfg.model.id

import android.content.SharedPreferences
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.network.okHttpClient
import com.example.tfg.repository.AuthorizationInterceptor
import com.example.tfg.repository.ErrorInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApolloDependencyInjection {

    @Singleton
    @Provides
    fun provideApolloClient(okHttpClient: OkHttpClient, sharedPreferences: SharedPreferences): ApolloClient{
        return ApolloClient.Builder().serverUrl("http://10.0.2.2:8080/graphql").addInterceptor(AuthorizationInterceptor {
            sharedPreferences.getString("access_token", null)
        }).addInterceptor(ErrorInterceptor()).okHttpClient(okHttpClient).build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient{
        return OkHttpClient.Builder().build()
    }

}