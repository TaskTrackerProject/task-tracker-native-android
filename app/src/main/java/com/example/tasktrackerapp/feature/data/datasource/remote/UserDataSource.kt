package com.example.tasktrackerapp.feature.data.datasource.remote

import com.example.tasktrackerapp.feature.domain.model.common.ResultModel
import com.example.tasktrackerapp.feature.domain.entity.remote.user.UserEmailLoginPayloadEntity
import com.example.tasktrackerapp.feature.domain.entity.remote.user.UserRegisterPayloadEntity
import com.example.tasktrackerapp.feature.domain.entity.remote.user.UserUsernameLoginPayloadEntity
import com.example.tasktrackerapp.feature.domain.entity.remote.user.UserVerifyPayloadEntity
import com.example.tasktrackerapp.feature.domain.model.login.LoginResultModel

interface UserDataSource {
    suspend fun registerUser(payload: UserRegisterPayloadEntity): ResultModel<String>

    suspend fun verifyUser(payload: UserVerifyPayloadEntity): ResultModel<String>

    suspend fun loginViaEmail(payload: UserEmailLoginPayloadEntity): LoginResultModel

    suspend fun loginViaUsername(payload: UserUsernameLoginPayloadEntity): LoginResultModel
}