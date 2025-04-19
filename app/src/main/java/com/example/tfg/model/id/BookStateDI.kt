package com.example.tfg.model.id

import com.example.tfg.model.book.BookState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class BookStateDI {

    @Singleton
    @Provides
    fun provideBookState(): BookState {
        return BookState()
    }
}