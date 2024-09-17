package com.example.tasktrackerapp.core.model

import com.example.tasktrackerapp.core.utils.UIText

data class SuccessModel<T: Any>(
    val message: UIText = UIText.DynamicString(""),
    val data: T? = null,
)
