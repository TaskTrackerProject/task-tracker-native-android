package com.example.tasktrackerapp.feature.domain.model.common

import com.example.tasktrackerapp.core.utils.UIText

data class ResultModel<T: Any>(
    val isSuccess: Boolean,
    val message: UIText = UIText.DynamicString(""),
    val data: T? = null,
)