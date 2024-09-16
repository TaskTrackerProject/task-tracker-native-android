package com.example.tasktrackerapp.feature.data.datasource.remote

import com.example.tasktrackerapp.feature.data.api.UserService
import com.example.tasktrackerapp.feature.domain.entity.remote.CommonResponseEntity
import com.example.tasktrackerapp.feature.domain.entity.remote.user.UserRegisterDataResultEntity
import com.example.tasktrackerapp.feature.domain.entity.remote.user.UserRegisterPayloadEntity
import retrofit2.Response
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val userService: UserService,
) : UserDataSource {
    override suspend fun registerUser(payload: UserRegisterPayloadEntity): Response<CommonResponseEntity<UserRegisterDataResultEntity>> {
        return userService.registerUser(payload);
    }
}