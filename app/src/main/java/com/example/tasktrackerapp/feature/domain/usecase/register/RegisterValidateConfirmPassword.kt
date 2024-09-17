package com.example.tasktrackerapp.feature.domain.usecase.register

import com.example.tasktrackerapp.R
import com.example.tasktrackerapp.core.model.ResultModel
import com.example.tasktrackerapp.core.utils.UIText

class RegisterValidateConfirmPassword {
    operator fun invoke(pass1: String, pass2: String) : ResultModel<Any> {
        val confirmPassword = pass2.trim()
        if (confirmPassword.isBlank() || confirmPassword.isEmpty()) {
            return ResultModel(
                isSuccess = false,
                message = UIText.StringResource(R.string.field_is_empty),
            )
        } else if (confirmPassword != pass1) {
            return ResultModel(
                isSuccess = false,
                message = UIText.StringResource(R.string.confirm_pass_not_match),
            )
        }
        return ResultModel(isSuccess = true)
    }
}