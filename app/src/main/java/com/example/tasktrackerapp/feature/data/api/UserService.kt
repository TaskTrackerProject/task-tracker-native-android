package com.example.tasktrackerapp.feature.data.api

import com.example.tasktrackerapp.feature.domain.entity.remote.CommonResponseEntity
import com.example.tasktrackerapp.feature.domain.entity.remote.user.UserRegisterDataResultEntity
import com.example.tasktrackerapp.feature.domain.entity.remote.user.UserRegisterPayloadEntity
import com.example.tasktrackerapp.feature.domain.entity.remote.user.UserTokenDataResultEntity
import com.example.tasktrackerapp.feature.domain.entity.remote.user.UserVerifyPayloadEntity
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    @POST("api/user/register")
    suspend fun registerUser(@Body payload: UserRegisterPayloadEntity): Response<CommonResponseEntity<UserRegisterDataResultEntity>>

    @POST("api/user/verify")
    suspend fun verifyUser(@Body payload: UserVerifyPayloadEntity) : Response<CommonResponseEntity<UserTokenDataResultEntity>>
}