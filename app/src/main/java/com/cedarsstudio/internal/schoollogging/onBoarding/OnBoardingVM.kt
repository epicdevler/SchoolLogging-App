package com.cedarsstudio.internal.schoollogging.onBoarding

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class OnBoardingVM @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _shouldGetStarted: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val shouldGetStarted = _shouldGetStarted.asStateFlow()


    fun setShouldGetStarted(state: Boolean){
        _shouldGetStarted.tryEmit(state)
    }

}