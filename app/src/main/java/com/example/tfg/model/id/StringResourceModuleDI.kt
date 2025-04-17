package com.example.tfg.model.id

import android.content.Context
import com.example.tfg.ui.common.StringResourcesProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StringResourceModuleDI {
        @Provides
        @Singleton
        fun provideStringResource(@ApplicationContext context: Context): StringResourcesProvider{
            return StringResourcesProvider(context)
        }
}