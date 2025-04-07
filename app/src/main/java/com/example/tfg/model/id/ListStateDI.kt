package com.example.tfg.model.id

import com.example.tfg.model.booklist.ListsState
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
    fun provideListState(): ListsState {
        return ListsState()
    }

}