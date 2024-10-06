package com.example.tasktrackerapp.di

import com.example.tasktrackerapp.feature.data.datasource.local.SharedPrefDataSource
import com.example.tasktrackerapp.feature.data.datasource.remote.UserDataSource
import com.example.tasktrackerapp.feature.data.repository.UserRepositoryImpl
import com.example.tasktrackerapp.feature.data.repository.UtilityRepositoryImpl
import com.example.tasktrackerapp.feature.domain.repository.UserRepository
import com.example.tasktrackerapp.feature.domain.repository.UtilityRepository
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(
        userDataSource: UserDataSource
    ): UserRepository {
        return UserRepositoryImpl(userDataSource)
    }

    @Provides
    @Singleton
    fun provideUtilityRepository(gson: Gson): UtilityRepository {
        return UtilityRepositoryImpl(gson)
    }

}