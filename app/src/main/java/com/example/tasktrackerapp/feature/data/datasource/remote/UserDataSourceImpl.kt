package com.example.tasktrackerapp.feature.data.datasource.remote

import com.example.tasktrackerapp.core.model.ResultModel
import com.example.tasktrackerapp.core.utils.UIText
import com.example.tasktrackerapp.feature.data.api.UserService
import com.example.tasktrackerapp.feature.domain.entity.remote.user.UserRegisterPayloadEntity
import com.example.tasktrackerapp.feature.domain.entity.remote.user.UserVerifyPayloadEntity
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val userService: UserService,
) : UserDataSource {
    override suspend fun registerUser(payload: UserRegisterPayloadEntity): ResultModel<String> {
        try {
            val result = userService.registerUser(payload)
            if (result.code() == 201 && result.body() != null) {
                val userId = result.body()?.data?.id ?: ""
                return ResultModel(
                    isSuccess = true,
                    message = UIText.DynamicString(
                        result.body()!!.message ?: "Registration success"
                    ),
                    data = userId,
                )
            } else {
                return ResultModel(
                    isSuccess = false,
                    message = UIText.DynamicString(result.body()?.message ?: "Registration failed")
                )
            }
        } catch (e: Exception) {
            return ResultModel(
                isSuccess = false,
                message = UIText.DynamicString(value = e.message ?: "Registration failed")
            )
        }
    }

    override suspend fun verifyUser(payload: UserVerifyPayloadEntity): ResultModel<String> {
        try {
            val result = userService.verifyUser(payload)
            val resultBody = result.body()
            if (resultBody != null) {
                val resultStatus = resultBody.status
                if (resultStatus == "success") {
                    return ResultModel(
                        isSuccess = true,
                        message = UIText.DynamicString(
                            resultBody.message ?: "Verification success"
                        ),
                        data = result.body()!!.data!!.token,
                    )
                } else {
                    return ResultModel(
                        isSuccess = false,
                        message = UIText.DynamicString(
                            resultBody.message ?: "Verification failed"
                        )
                    )
                }
            }
        } catch (e: Exception) {
            return ResultModel(
                isSuccess = false,
                message = UIText.DynamicString(e.message ?: "Verification failed")
            )
        }
        return ResultModel(
            isSuccess = false,
            message = UIText.DynamicString("Verification failed")
        )
    }
}