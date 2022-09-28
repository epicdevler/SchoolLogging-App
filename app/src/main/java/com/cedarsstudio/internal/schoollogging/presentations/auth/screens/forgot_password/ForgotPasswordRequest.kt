package com.cedarsstudio.internal.schoollogging.presentations.auth.screens.forgot_password

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.cedarsstudio.internal.schoollogging.databinding.ForgotPasswordRequestOtpFragmentBinding
import com.cedarsstudio.internal.schoollogging.presentations.auth.screens.SignIn
import com.cedarsstudio.internal.schoollogging.presentations.auth.vm.AuthVM
import com.cedarsstudio.internal.schoollogging.presentations.modals.ResponseModal
import com.cedarsstudio.internal.schoollogging.utils.State
import com.cedarsstudio.internal.schoollogging.utils.handleBackPressed
import com.cedarsstudio.internal.schoollogging.utils.toAdminScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ForgotPasswordRequest : Fragment() {

    private var _binding: ForgotPasswordRequestOtpFragmentBinding? = null
    private val binding
        get() = _binding!!
    private val vm: AuthVM by viewModels()

    private lateinit var responseModal: ResponseModal
    private lateinit var loaderModal: ResponseModal

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        ForgotPasswordRequestOtpFragmentBinding.inflate(inflater, container, false)
            .let { _binding = it }

        initUi()

        handleUI()

        initObservers()
        return binding.root
    }


    private fun initObservers() {

        lifecycleScope.launch {
            vm.uiState.collect { uiState ->
                Log.e(SignIn.TAG, "initObservers: $uiState")
                binding.apply {
                    if (uiState.state == State.Loading) {
                        emailInput.isEnabled = !emailInput.isEnabled
                        authForgotSubmit.isEnabled = !authForgotSubmit.isEnabled
                    } else {
                        emailInput.isEnabled = !emailInput.isEnabled
                        authForgotSubmit.isEnabled = !authForgotSubmit.isEnabled
                    }
                }
                when (uiState.state) {
                    State.Success -> {
                        responseModal.show(true, "${uiState.msg}", false) {
                            toAdminScreen(true)
                            responseModal.dismiss()
                        }
                    }
                    State.Error -> {
                        responseModal.show(msg = "${uiState.msg}", isCancellable = true)
                    }
                    else -> {}
                }
            }
        }

    }

    private fun handleUI() {
        binding.apply {
            authForgotSubmit.setOnClickListener {
//                findNavController().navigate(ForgotPasswordRequestDirections.actionForgotPasswordRequestToForgotPasswordOTP())
                vm.requestResetPassword()
            }
        }
    }

    private fun initUi() {
        responseModal = ResponseModal(requireActivity())
        loaderModal = ResponseModal(requireActivity())
        binding.apply {
            emailInput.addTextChangedListener {
                vm._email = it.toString()
            }
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        responseModal.dismiss()
    }
}