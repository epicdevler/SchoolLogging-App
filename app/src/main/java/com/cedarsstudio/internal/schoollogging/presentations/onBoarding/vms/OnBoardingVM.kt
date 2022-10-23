package com.cedarsstudio.internal.schoollogging.presentations.onBoarding.vms

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.cedarsstudio.internal.schoollogging.remote.auth.repos.Auth
import com.cedarsstudio.internal.schoollogging.remote.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnBoardingVM @Inject constructor(
    private val auth: Auth, private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        private const val TAG = "ONBOARDING_VM"
    }

    fun isSignedIn(): Boolean {
        val result = auth.isSignedIn()
        return if (result is Response.Success) {
            result.data!!
        } else {
            false
        }
    }

}