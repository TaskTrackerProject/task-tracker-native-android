package com.example.tasktrackerapp.feature.domain.usecase

import com.example.tasktrackerapp.R
import com.example.tasktrackerapp.feature.domain.model.common.ResultModel
import com.example.tasktrackerapp.core.utils.UIText
import com.example.tasktrackerapp.core.utils.Utility

class ValidateEmptyField {
    operator fun invoke(data: String) : ResultModel<Any> {
        val fieldVal = Utility.capitalizedInitialLetter(data.trim())
        return if (fieldVal.isBlank() || fieldVal.isEmpty()) {
            ResultModel(
                isSuccess = false,
                message = UIText.StringResource(R.string.field_is_empty),
            )
        } else {
            ResultModel(isSuccess = true)
        }
    }
}