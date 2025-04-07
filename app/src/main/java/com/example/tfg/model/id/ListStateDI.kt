package com.example.tfg.model.id

import com.example.tfg.ui.lists.UserListState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ListStateDI {

    @Singleton
    @Provides
    fun provideListState(): UserListState {
        return UserListState()
    }

}