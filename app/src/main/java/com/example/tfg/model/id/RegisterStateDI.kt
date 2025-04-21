package com.example.tfg.model.id

import com.example.tfg.model.user.UserRegistrationState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RegisterStateDI {

    @Singleton
    @Provides
    fun provideRegisterState(): UserRegistrationState {
        return UserRegistrationState()
    }
}