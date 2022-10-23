package com.cedarsstudio.internal.schoollogging.presentations.admins.vms

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cedarsstudio.internal.schoollogging.presentations.admins.screens.AuthType
import com.cedarsstudio.internal.schoollogging.remote.students.StudentLists
import com.cedarsstudio.internal.schoollogging.remote.utils.Response
import com.cedarsstudio.internal.schoollogging.utils.State
import com.cedarsstudio.internal.schoollogging.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthStudentVM @Inject constructor(
    private val savedStateHandle: SavedStateHandle, private val studentLists: StudentLists
) : ViewModel() {

    private var _uiState: MutableStateFlow<UiState<String>> = MutableStateFlow(UiState(state = State.None))
    val uiState = _uiState.asStateFlow()
    var authType: MutableState<AuthType> = mutableStateOf(AuthType.None)

    fun authStudent(id: String, type: AuthType) = viewModelScope.launch {
        _uiState.tryEmit(UiState(msg = "${type.name} Student with $id"))
        when (val response = studentLists.setOrToggleAuthState(id, type)) {
            is Response.Success -> _uiState.tryEmit(
                UiState(
                    state = State.Success,
                    msg = response.msg
                )
            )
            else -> _uiState.tryEmit(UiState(state = State.Error, msg = response.msg))
        }
    }

    fun setAuthType(_authType: String) {
        Log.e("TAG", "setAuthType: $_authType")
        if (_authType.equals(AuthType.SignIn.name, true)) {
            authType.value = AuthType.SignIn
        } else {
            authType.value = AuthType.SignOut
        }
    }

    fun setErrorState(t: Throwable) {
        _uiState.tryEmit(UiState(state = State.Error, msg = t.message))
    }

}