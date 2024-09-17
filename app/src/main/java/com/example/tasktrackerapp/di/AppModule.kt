package com.example.tasktrackerapp.di

import com.example.tasktrackerapp.core.constants.AppConstants
import com.example.tasktrackerapp.feature.data.api.UserService
import com.example.tasktrackerapp.feature.data.datasource.remote.UserDataSource
import com.example.tasktrackerapp.feature.data.datasource.remote.UserDataSourceImpl
import com.example.tasktrackerapp.feature.data.repository.UserRepositoryImpl
import com.example.tasktrackerapp.feature.domain.repository.UserRepository
import com.example.tasktrackerapp.feature.domain.usecase.ValidateEmptyField
import com.example.tasktrackerapp.feature.domain.usecase.register.RegisterUseCase
import com.example.tasktrackerapp.feature.domain.usecase.register.RegisterUser
import com.example.tasktrackerapp.feature.domain.usecase.register.RegisterValidateConfirmPassword
import com.example.tasktrackerapp.feature.domain.usecase.register.RegisterValidateEmail
import com.example.tasktrackerapp.feature.domain.usecase.register.RegisterValidatePassword
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit() : Retrofit {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

        val httpClient = OkHttpClient().newBuilder().apply {
            addInterceptor(httpLoggingInterceptor)
        }

        httpClient.apply {
            readTimeout(60, TimeUnit.SECONDS)
        }

        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

        return Retrofit.Builder()
            .baseUrl(AppConstants.APP_BASE_URL)
            .client(httpClient.build())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun provideUserService(retrofit: Retrofit) : UserService {
        return retrofit.create(UserService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserDataSource(userService: UserService) : UserDataSource {
        return UserDataSourceImpl(userService)
    }

    @Provides
    @Singleton
    fun provideUserRepository(userDataSource: UserDataSource) : UserRepository {
        return UserRepositoryImpl(userDataSource)
    }

    @Provides
    @Singleton
    fun provideRegisterScreenUseCase(userRepository: UserRepository) : RegisterUseCase {
        return RegisterUseCase(
            registerUser = RegisterUser(userRepository),
            validateEmail = RegisterValidateEmail(),
            validatePassword = RegisterValidatePassword(),
            validateConfirmPassword = RegisterValidateConfirmPassword(),
            validateEmptyField = ValidateEmptyField(),
        )
    }
}