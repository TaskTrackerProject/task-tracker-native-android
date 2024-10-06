package com.example.tasktrackerapp.feature.data.datasource.remote

import com.example.tasktrackerapp.feature.domain.model.common.ResultModel
import com.example.tasktrackerapp.core.utils.UIText
import com.example.tasktrackerapp.feature.data.api.UserService
import com.example.tasktrackerapp.feature.domain.entity.remote.CommonResponseEntity
import com.example.tasktrackerapp.feature.domain.entity.remote.user.UserEmailLoginPayloadEntity
import com.example.tasktrackerapp.feature.domain.entity.remote.user.UserLoginDataResultEntity
import com.example.tasktrackerapp.feature.domain.entity.remote.user.UserRegisterDataResultEntity
import com.example.tasktrackerapp.feature.domain.entity.remote.user.UserRegisterPayloadEntity
import com.example.tasktrackerapp.feature.domain.entity.remote.user.UserTokenDataResultEntity
import com.example.tasktrackerapp.feature.domain.entity.remote.user.UserUsernameLoginPayloadEntity
import com.example.tasktrackerapp.feature.domain.entity.remote.user.UserVerifyPayloadEntity
import com.example.tasktrackerapp.feature.domain.model.login.LoginResultModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val userService: UserService,
    private val gson: Gson,
) : UserDataSource {
    override suspend fun registerUser(payload: UserRegisterPayloadEntity): ResultModel<String> {
        try {
            val result = userService.registerUser(payload)
            if (result.isSuccessful) {
                val resultBody = result.body()
                if (resultBody != null) {
                    val userId = resultBody.data?.id ?: ""
                    return ResultModel(
                        isSuccess = true,
                        message = UIText.DynamicString(
                            result.body()!!.message ?: "Registration success"
                        ),
                        data = userId,
                    )
                }
            } else {
                val errorBody = result.errorBody()?.string()
                if (errorBody != null) {
                    val type = object :
                        TypeToken<CommonResponseEntity<UserRegisterDataResultEntity>>() {}.type
                    val resultBody: CommonResponseEntity<UserRegisterDataResultEntity> =
                        gson.fromJson(errorBody, type)
                    val resultStatus = resultBody.status
                    if (resultStatus == "error") {
                        return ResultModel(
                            isSuccess = false,
                            message = UIText.DynamicString(
                                resultBody.message ?: "Registration failed"
                            )
                        )
                    }
                }
            }
        } catch (e: Exception) {
            return ResultModel(
                isSuccess = false,
                message = UIText.DynamicString(value = e.message ?: "Registration failed")
            )
        }
        return ResultModel(
            isSuccess = false,
            message = UIText.DynamicString(value = "Registration failed")
        )
    }

    override suspend fun verifyUser(payload: UserVerifyPayloadEntity): ResultModel<String> {
        try {
            val result = userService.verifyUser(payload)
            if (result.isSuccessful) {
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
                    }
                }
            } else {
                val errorString = result.errorBody()?.string()
                if (errorString != null) {
                    val type = object :
                        TypeToken<CommonResponseEntity<UserTokenDataResultEntity>>() {}.type
                    val resultBody: CommonResponseEntity<UserTokenDataResultEntity> =
                        gson.fromJson(errorString, type)
                    val resultStatus = resultBody.status
                    if (resultStatus == "error") {
                        return ResultModel(
                            isSuccess = false,
                            message = UIText.DynamicString(
                                resultBody.message ?: "Verification failed"
                            )
                        )
                    }
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
            if (result.isSuccessful) {
                val resultBody = result.body()
                if (resultBody != null) {
                    val resultStatus = resultBody.status
                    if (resultStatus == "success") {
                        return LoginResultModel(
                            isSuccess = true,
                            message = UIText.DynamicString(
                                resultBody.message ?: "Login success",
                            ),
                            token = resultBody.data!!.token!!,
                            id = resultBody.data.id!!,
                        )
                    }
                }
            } else {
                val errorBody = result.errorBody()?.string()
                if (errorBody != null) {
                    val type = object :
                        TypeToken<CommonResponseEntity<UserLoginDataResultEntity>>() {}.type
                    val resultBody: CommonResponseEntity<UserLoginDataResultEntity> =
                        gson.fromJson(errorBody, type)
                    val resultStatus = resultBody.status
                    if (resultStatus == "error") {
                        val errors = resultBody.errors ?: emptyList()
                        if (errors.isNotEmpty()) {
                            val error = errors[0]
                            val isNotVerified = error.code == "not_verified"
                            val email = resultBody.data!!.email ?: ""
                            val id = resultBody.data.id ?: ""
                            return LoginResultModel(
                                isSuccess = false,
                                message = UIText.DynamicString(
                                    resultBody.message ?: "Login failed",
                                ),
                                isNotVerified = isNotVerified,
                                email = email,
                                id = id,
                            )
                        }
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
        try {
            val result = userService.loginViaUsername(payload)
            if (result.isSuccessful) {
                val resultBody = result.body()
                if (resultBody != null) {
                    val resultStatus = resultBody.status
                    if (resultStatus == "success") {
                        return LoginResultModel(
                            isSuccess = true,
                            message = UIText.DynamicString(
                                resultBody.message ?: "Login success",
                            ),
                            token = resultBody.data!!.token!!,
                            id = resultBody.data.id!!,
                        )
                    }
                }
            } else {
                val errorBody = result.errorBody()?.string()
                if (errorBody != null) {
                    val type = object :
                        TypeToken<CommonResponseEntity<UserLoginDataResultEntity>>() {}.type
                    val resultBody: CommonResponseEntity<UserLoginDataResultEntity> =
                        gson.fromJson(errorBody, type)
                    val resultStatus = resultBody.status
                    if (resultStatus == "error") {
                        val errors = resultBody.errors ?: emptyList()
                        if (errors.isNotEmpty()) {
                            val error = errors[0]
                            val isNotVerified = error.code == "not_verified"
                            val email = resultBody.data!!.email ?: ""
                            val id = resultBody.data.id ?: ""
                            return LoginResultModel(
                                isSuccess = false,
                                message = UIText.DynamicString(
                                    resultBody.message ?: "Login failed",
                                ),
                                isNotVerified = isNotVerified,
                                email = email,
                                id = id,
                            )
                        }
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
}