package com.example.tasktrackerapp.feature.presentation.screen.verification.common

import androidx.core.app.NotificationCompat.MessagingStyle.Message
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasktrackerapp.core.model.Either
import com.example.tasktrackerapp.core.utils.UIText
import com.example.tasktrackerapp.feature.domain.usecase.verification.VerificationUseCase
import com.example.tasktrackerapp.feature.presentation.screen.register.common.RegisterViewModel.UIEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerificationViewModel @Inject constructor(
    private val useCase: VerificationUseCase,
) : ViewModel() {
    sealed class UIEvents {
        data class ShowSnackBar(val message: UIText) : UIEvents()
        data class ShowToast(val message: UIText) : UIEvents()
        data object GoToHomeScreen : UIEvents()
    }

    private val _state = MutableStateFlow(VerificationState())
    val state = _state.asStateFlow()

    private val _uiEvents = MutableSharedFlow<UIEvents>()
    val uiEvents = _uiEvents.asSharedFlow()

    private fun filterData(data: String): String {
        val text = if (data.length <= 4) {
            data
        } else {
            data.take(4)
        }
        return text;
    }

    fun determineHighLight() {
        val field1 = _state.value.firstFieldVal
        val field2 = _state.value.secondFieldVal
        val field3 = _state.value.thirdFieldVal
        val field4 = _state.value.fourthFieldVal

        if (field1.isNotEmpty() && field2.isNotEmpty() && field3.isNotEmpty() && field4.isNotEmpty()) {
            _state.value = state.value.copy(
                field1Highlight = false,
                field2Highlight = false,
                field3Highlight = false,
                field4Highlight = true,
            )
        } else if (field1.isNotEmpty() && field2.isNotEmpty() && field3.isNotEmpty() && field4.isEmpty()) {
            _state.value = state.value.copy(
                field1Highlight = false,
                field2Highlight = false,
                field3Highlight = false,
                field4Highlight = true,
            )
        } else if (field1.isNotEmpty() && field2.isNotEmpty() && field3.isEmpty() && field4.isEmpty()) {
            _state.value = state.value.copy(
                field1Highlight = false,
                field2Highlight = false,
                field3Highlight = true,
                field4Highlight = false,
            )
        } else if (field1.isNotEmpty() && field2.isEmpty() && field3.isEmpty() && field4.isEmpty()) {
            _state.value = state.value.copy(
                field1Highlight = false,
                field2Highlight = true,
                field3Highlight = false,
                field4Highlight = false,
            )
        } else if (field1.isNotEmpty() && field2.isEmpty() && field3.isEmpty() && field4.isEmpty()) {
            _state.value = state.value.copy(
                field1Highlight = true,
                field2Highlight = false,
                field3Highlight = false,
                field4Highlight = false,
            )
        } else if (field1.isEmpty() && field2.isEmpty() && field3.isEmpty() && field4.isEmpty()) {
            _state.value = state.value.copy(
                field1Highlight = true,
                field2Highlight = false,
                field3Highlight = false,
                field4Highlight = false,
            )
        }
    }

    fun setOTPData(data: String) {
        val value = filterData(data)
        _state.value = state.value.copy(
            otpVal = value,
        )
        when (value.length) {
            0 -> {
                _state.value = state.value.copy(
                    firstFieldVal = "",
                    secondFieldVal = "",
                    thirdFieldVal = "",
                    fourthFieldVal = "",
                )
            }

            1 -> {
                _state.value = state.value.copy(
                    firstFieldVal = value.last().toString(),
                    secondFieldVal = "",
                    thirdFieldVal = "",
                    fourthFieldVal = "",
                )
            }

            2 -> {
                _state.value = state.value.copy(
                    secondFieldVal = value.last().toString(),
                    thirdFieldVal = "",
                    fourthFieldVal = "",
                )
            }

            3 -> {
                _state.value = state.value.copy(
                    thirdFieldVal = value.last().toString(),
                    fourthFieldVal = "",
                )
            }

            4 -> {
                _state.value = state.value.copy(
                    fourthFieldVal = value.last().toString(),
                )
            }
        }
        determineHighLight()
    }

    fun verify(userId: String) {
        val otp =
            _state.value.firstFieldVal + _state.value.secondFieldVal + _state.value.thirdFieldVal + _state.value.fourthFieldVal
        viewModelScope.launch {
            _state.value = state.value.copy(isLoading = true)
            when(val result = useCase.verifyUser.invoke(userId, otp)) {
                is Either.Left -> {
                    _uiEvents.emit(UIEvents.ShowSnackBar(result.value.message))
                }
                is Either.Right -> {
                    _uiEvents.emit(UIEvents.ShowToast(result.value.message))
                    _uiEvents.emit(UIEvents.GoToHomeScreen)
                }
            }
            _state.value = state.value.copy(isLoading = false)
        }
    }
}