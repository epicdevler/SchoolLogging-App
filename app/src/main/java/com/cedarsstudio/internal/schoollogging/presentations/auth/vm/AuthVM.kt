package com.cedarsstudio.internal.schoollogging.presentations.auth.vm

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cedarsstudio.internal.schoollogging.remote.auth.repos.Auth
import com.cedarsstudio.internal.schoollogging.remote.utils.Response
import com.cedarsstudio.internal.schoollogging.utils.State.Error
import com.cedarsstudio.internal.schoollogging.utils.State.Success
import com.cedarsstudio.internal.schoollogging.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthVM @Inject constructor(
    private val auth: Auth, private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState: Channel<UiState<String>> = Channel()
    val uiState = _uiState.receiveAsFlow()
    var _name: String
        set(value) = savedStateHandle.set("_name", value)
        get() {
            return savedStateHandle["_name"] ?: ""
        }
    var _email: String
        set(value) = savedStateHandle.set("_email", value)
        get() {
            return savedStateHandle["_email"] ?: ""
        }
    var _password: String
        set(value) = savedStateHandle.set("_password", value)
        get() {
            return savedStateHandle["_password"] ?: ""
        }
    var _confirmPassword: String
        set(value) = savedStateHandle.set("_confirmPassword", value)
        get() {
            return savedStateHandle["_confirmPassword"] ?: ""
        }
    var _otpCode: String
        set(value) = savedStateHandle.set("_otpCode", value)
        get() {
            return savedStateHandle["_otpCode"] ?: ""
        }
    var _rememberMe: Boolean
        set(value) = savedStateHandle.set("_rememberMe", value)
        get() {
            return savedStateHandle["_rememberMe"] ?: false
        }


    fun sigIn() = viewModelScope.launch {
        _uiState.trySendBlocking(UiState())
        when (val result = auth.signIn(_rememberMe, _email, _password)) {
            is Response.Success -> {
                _uiState.trySendBlocking(
                    UiState(state = Success, msg = result.msg)
                )
            }
            else -> _uiState.trySendBlocking(
                UiState(state = Error, msg = result.msg)
            )

        }
    }

    fun sigUp() = viewModelScope.launch {
        _uiState.trySendBlocking(UiState())
        when (val result = auth.signUp(_name, _email, _password)) {
            is Response.Success -> {
                _uiState.trySendBlocking(
                    UiState(state = Success, msg = result.msg)
                )
            }
            else -> _uiState.trySendBlocking(
                UiState(state = Error, msg = result.msg)
            )

        }
    }

    fun requestResetPassword() = viewModelScope.launch {
        _uiState.trySendBlocking(UiState())
        when (val result = auth.requestResetPassword(_email)) {
            is Response.Success -> {
                _uiState.trySendBlocking(
                    UiState(state = Success, msg = result.msg)
                )
            }
            else -> _uiState.trySendBlocking(
                UiState(state = Error, msg = result.msg)
            )

        }
    }

}