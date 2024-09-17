package com.example.tasktrackerapp.feature.domain.usecase.register

import com.example.tasktrackerapp.R
import com.example.tasktrackerapp.core.constants.AppConstants
import com.example.tasktrackerapp.core.model.ResultModel
import com.example.tasktrackerapp.core.utils.UIText

class RegisterValidatePassword {
    operator fun invoke(data: String) : ResultModel<Any> {
        val password = data.trim()
        if (password.isBlank() || password.isEmpty()) {
            return ResultModel(
                isSuccess = false,
                message = UIText.StringResource(R.string.field_is_empty),
            )
        } else if (password.length < AppConstants.PASSWORD_MIN_LEN) {
            return ResultModel(
                isSuccess = false,
                message = UIText.StringResource(
                    R.string.password_min_char,
                    AppConstants.PASSWORD_MIN_LEN,
                ),
            )
        } else if (!password.any { it.isLowerCase() }) {
            return ResultModel(
                isSuccess = false,
                message = UIText.StringResource(R.string.password_include_lowercase),
            )
        } else if (!password.any { it.isUpperCase() }) {
            return ResultModel(
                isSuccess = false,
                message = UIText.StringResource(R.string.password_include_uppercase),
            )
        } else if (!password.any { it.isDigit() }) {
            return ResultModel(
                isSuccess = false,
                message = UIText.StringResource(R.string.password_include_number),
            )
        } else if (password.all { it.isLetterOrDigit() }) {
            return ResultModel(
                isSuccess = false,
                message = UIText.StringResource(R.string.password_include_special_char),
            )
        }
        return ResultModel(isSuccess = true)
    }
}