package com.example.tasktrackerapp.feature.domain.usecase.register

import com.example.tasktrackerapp.R
import com.example.tasktrackerapp.feature.domain.model.common.ResultModel
import com.example.tasktrackerapp.core.utils.UIText
import com.example.tasktrackerapp.core.utils.Utility

class RegisterValidateEmail {
    operator fun invoke(data: String) : ResultModel<Any> {
        val email = data.trim()
        if (email.isBlank() || email.isEmpty()) {
            return ResultModel(
                isSuccess = false,
                message = UIText.StringResource(R.string.field_is_empty),
            )
        } else if (!Utility.isValidEmail(email)) {
            return ResultModel(
                isSuccess = false,
                message = UIText.StringResource(R.string.enter_valid_email_add)
            )
        }
        return ResultModel(isSuccess = true)
    }
}