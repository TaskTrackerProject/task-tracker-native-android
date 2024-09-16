package com.example.tasktrackerapp.feature.data.api

import com.example.tasktrackerapp.feature.domain.entity.remote.CommonResponseEntity
import com.example.tasktrackerapp.feature.domain.entity.remote.user.UserRegisterDataResultEntity
import com.example.tasktrackerapp.feature.domain.entity.remote.user.UserRegisterPayloadEntity
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    @POST("api/user/register")
    suspend fun registerUser(@Body payload: UserRegisterPayloadEntity): Response<CommonResponseEntity<UserRegisterDataResultEntity>>
}