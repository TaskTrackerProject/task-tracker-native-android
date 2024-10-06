package com.example.tasktrackerapp.di

import android.app.Application
import android.content.Context
import com.example.tasktrackerapp.core.constants.AppConstants
import com.example.tasktrackerapp.feature.data.api.UserService
import com.example.tasktrackerapp.feature.data.datasource.local.SharedPrefDataSource
import com.example.tasktrackerapp.feature.data.datasource.local.impl.SharedPrefDataSourceImpl
import com.example.tasktrackerapp.feature.data.datasource.remote.UserDataSource
import com.example.tasktrackerapp.feature.data.datasource.remote.impl.UserDataSourceImpl
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideUserDataSource(userService: UserService, gson: Gson): UserDataSource {
        return UserDataSourceImpl(userService, gson)
    }

    @Provides
    @Singleton
    fun provideSharedPrefDataSource(app: Application): SharedPrefDataSource {
        val sharedPreferences =
            app.getSharedPreferences(AppConstants.SHARED_PREF_NAME, Context.MODE_PRIVATE)
        return SharedPrefDataSourceImpl(sharedPreferences)
    }
}