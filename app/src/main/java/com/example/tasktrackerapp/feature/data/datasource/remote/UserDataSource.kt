package com.example.tasktrackerapp.feature.data.datasource.remote

import com.example.tasktrackerapp.core.model.ResultModel
import com.example.tasktrackerapp.feature.domain.entity.remote.CommonResponseEntity
import com.example.tasktrackerapp.feature.domain.entity.remote.user.UserRegisterDataResultEntity
import com.example.tasktrackerapp.feature.domain.entity.remote.user.UserRegisterPayloadEntity
import com.example.tasktrackerapp.feature.domain.entity.remote.user.UserTokenDataResultEntity
import com.example.tasktrackerapp.feature.domain.entity.remote.user.UserVerifyPayloadEntity
import retrofit2.Response

interface UserDataSource {
    suspend fun registerUser(payload: UserRegisterPayloadEntity): ResultModel<String>

    suspend fun verifyUser(payload: UserVerifyPayloadEntity): ResultModel<String>
}