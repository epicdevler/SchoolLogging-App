package com.cedarsstudio.internal.schoollogging.presentations.auth.vm

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.cedarsstudio.internal.schoollogging.remote.auth.models.AdminProfile
import com.cedarsstudio.internal.schoollogging.remote.auth.repos.Auth
import com.cedarsstudio.internal.schoollogging.remote.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileVM @Inject constructor(
    private val auth: Auth
) : ViewModel() {

    val admin: MutableState<AdminProfile> = mutableStateOf(AdminProfile())

    init {
        when (val result = auth.getUser()) {
            is Response.Success -> admin.value = result.data!!
            else -> {}
        }
    }

    suspend fun signOut(): Response<String> = auth.signOut()

}