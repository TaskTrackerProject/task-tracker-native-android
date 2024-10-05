package com.example.tasktrackerapp.feature.presentation.screen.verification.common

data class VerificationState(
    val otpVal: String = "",
    val firstFieldVal: String = "",
    val secondFieldVal: String = "",
    val thirdFieldVal: String = "",
    val fourthFieldVal: String = "",
    val field1Highlight: Boolean = false,
    val field2Highlight: Boolean = false,
    val field3Highlight: Boolean = false,
    val field4Highlight: Boolean = false,
    val isLoading: Boolean = false,

)