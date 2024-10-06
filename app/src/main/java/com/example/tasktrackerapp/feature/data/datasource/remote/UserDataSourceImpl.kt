package com.example.tasktrackerapp.feature.data.datasource.remote

import com.example.tasktrackerapp.feature.domain.model.common.ResultModel
import com.example.tasktrackerapp.core.utils.UIText
import com.example.tasktrackerapp.feature.data.api.UserService
import com.example.tasktrackerapp.feature.domain.entity.remote.user.UserEmailLoginPayloadEntity
import com.example.tasktrackerapp.feature.domain.entity.remote.user.UserRegisterPayloadEntity
import com.example.tasktrackerapp.feature.domain.entity.remote.user.UserUsernameLoginPayloadEntity
import com.example.tasktrackerapp.feature.domain.entity.remote.user.UserVerifyPayloadEntity
import com.example.tasktrackerapp.feature.domain.model.login.LoginResultModel
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

    override suspend fun loginViaEmail(payload: UserEmailLoginPayloadEntity): LoginResultModel {
        try {
            val result = userService.loginViaEmail(payload)
            val resultBody = result.body()
            if (resultBody != null) {
                val resultStatus = resultBody.status
                if (resultStatus == "success") {
                    return LoginResultModel(
                        isSuccess = true,
                        message = UIText.DynamicString(
                            resultBody.message ?: "Login success",
                        ),
                        token = result.body()!!.data!!.token,
                    )
                } else if (resultStatus == "failed") {
                    val errors = resultBody.errors ?: emptyList()
                    if (errors.isNotEmpty()) {
                        val error = errors[0]
                        val isNotVerified = error.code == "not_verified"
                        return LoginResultModel(
                            isSuccess = false,
                            message = UIText.DynamicString(
                                resultBody.message ?: "Login failed",
                            ),
                            isNotVerified = isNotVerified,
                        )
                    }
                }
            }
        } catch (e: Exception) {
            return LoginResultModel(
                isSuccess = false,
                message = UIText.DynamicString(e.message ?: "Login failed"),
            )
        }
        return LoginResultModel(
            isSuccess = false,
            message = UIText.DynamicString("Login failed"),
        )
    }

    override suspend fun loginViaUsername(payload: UserUsernameLoginPayloadEntity): LoginResultModel {
        TODO("Not yet implemented")
    }
}