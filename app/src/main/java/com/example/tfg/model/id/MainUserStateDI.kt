package com.example.tfg.model.id

import com.example.tfg.model.user.MainUserState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MainUserStateDI {

    @Singleton
    @Provides
    fun provideMainUserState(): MainUserState {
        return MainUserState()
    }

}