package com.example.tasktrackerapp.feature.data.datasource.remote

import com.example.tasktrackerapp.feature.domain.entity.remote.CommonResponseEntity
import com.example.tasktrackerapp.feature.domain.entity.remote.user.UserRegisterDataResultEntity
import com.example.tasktrackerapp.feature.domain.entity.remote.user.UserRegisterPayloadEntity
import retrofit2.Response

interface UserDataSource {
    suspend fun registerUser(payload: UserRegisterPayloadEntity):
            Response<CommonResponseEntity<UserRegisterDataResultEntity>>
}